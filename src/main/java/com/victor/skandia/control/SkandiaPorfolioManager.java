package com.victor.skandia.control;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.victor.control.LoginController;
import com.victor.entity.Customer;
import com.victor.entity.CustomerPortfolio;
import com.victor.entity.FundValue;
import com.victor.entity.InvestmentFund;
import com.victor.entity.Portfolio;
import com.victor.entity.PortfolioFund;
import com.victor.entity.SkandiaAlgorithm;
import com.victor.service.CustomerPortfolioService;
import com.victor.service.CustomerService;
import com.victor.service.FundValueService;
import com.victor.service.InvestmentFundService;
import com.victor.service.PortfolioFundService;
import com.victor.service.PortfolioService;
import com.victor.service.SkandiaAlgorithmService;
import com.victor.skandia.model.CustomerGroupType;
import com.victor.utils.DataUtils;
import com.victor.utils.DateUtils;

/**
 * @author Wiktor Rosinski
 *
 */
@Controller
public class SkandiaPorfolioManager {
	
	@Autowired
	PortfolioService portfolioService;
	@Autowired
	CustomerPortfolioService customerPortfolioService;
	@Autowired
	PortfolioFundService portfolioFundService;
	@Autowired
	FundValueService fundValueService;
	@Autowired
	CustomerService customerService;
	@Autowired
	InvestmentFundService investmentFundService;
	@Autowired
	SkandiaAlgorithmService skandiaAlgorithmService;
	
	/** wyszukuje fundusze dla ktorych system wygenerowal sygnaly otwarcia */
	public List<InvestmentFund> generateOpenTransactionSingals(){
		List<InvestmentFund> fundsToOpen=new ArrayList<InvestmentFund>();
		List<InvestmentFund> activeFunds=investmentFundService.findAllActive();
		//dla kazdego aktywnego funduszu
		for(InvestmentFund fund:activeFunds){
			//jezeli zostanie wygenerowany sygnal otwarcia transakcji
			if(generateOpenSignal(fund))
				//dodaje do listy funduszy do przekazania uzytkownikowi
				fundsToOpen.add(fund);
		}
		sortFundList(fundsToOpen);
		return fundsToOpen;
	}
	
	/** sprawdza fundusze obecne w aktualnym portfelu i dzieli je na 3 listy spakowane w tablice:<br/>
	 * 0: fundusze z sygnalem otwarcia<br/>
	 * 1: fundusze z brakiem sygnalu<br/>
	 * 2: fundusze z sygnalem zamkniecia */
	public List<InvestmentFund>[] checkInvestmentFundFromPortfolio(){
		@SuppressWarnings("unchecked")
		List<InvestmentFund>[] fundListTable=new List[3];
		fundListTable[0]=new ArrayList<InvestmentFund>();//sygnaly otwacia
		fundListTable[1]=new ArrayList<InvestmentFund>();//brak sygnalow
		fundListTable[2]=new ArrayList<InvestmentFund>();//sygnaly zamkniecia
		
		Customer user=LoginController.getLoggedUser();
		if(user.getPortfolio()==null)
			return null;
		List<PortfolioFund> portfolioFundList=portfolioFundService.findByPortfolio(user.getPortfolio().getPorfolio());
		InvestmentFund fund;
		
		for(PortfolioFund portfolioFund: portfolioFundList){
			fund=portfolioFund.getInvestmentFund();
			if(generateOpenSignal(fund)){
				fundListTable[0].add(fund);
			}else if(generateCloseSignal(fund)){
				fundListTable[2].add(fund);
			}else{
				fundListTable[1].add(fund);
			}
		}
		
		sortFundList(fundListTable[0]);
		sortFundList(fundListTable[1]);
		sortFundList(fundListTable[2]);
		
		return fundListTable;	
	}
	

	
	/** sortuje liste funduszy wedlug stopy zwrotu od najwyzszej do najnizszej */
	private void sortFundList(List<InvestmentFund> fundList){
		boolean sorted =false;
		while(!sorted){
			sorted=true;
			for(int i=1;i<fundList.size();i++){
				if(fundList.get(i-1).getWeight().getRateOfProfit().compareTo(fundList.get(i).getWeight().getRateOfProfit())<0){
					InvestmentFund temp=fundList.get(i);
					fundList.set(i, fundList.get(i-1));
					fundList.set(i-1, temp);
					i--;
					sorted=false;
				}
			}
		}
	}

	public void createNewPortfolio(List<PortfolioFund> portfolioFundList, CustomerGroupType customerGroupType){
		if(customerGroupType==null)
			return;
		List<Customer> customerList=DataUtils.getCustomers(customerGroupType, customerService);
		
		if(customerList!=null)
			for(Customer customer:customerList)
				closeOldPortfolio(customer);
		createNewPortfolioAndSave(portfolioFundList, customerList);
		
	}
	

	/** generuje sygnal otwarcia transakcji dla funduszu na podstawie obecnej ceny jednostek - metoda do budowania portfela */
	private boolean generateOpenSignal(InvestmentFund fund){
		SkandiaAlgorithm openAlgorithm=fund.getOpeningAlgorithm();
		SkandiaAlgorithm closeAlgorithm=fund.getClosingAlgorithm();
		if(openAlgorithm==null || closeAlgorithm==null)
			return false;
		
		openAlgorithm.loadAlgorithm();
		closeAlgorithm.loadAlgorithm();
		int length;
		if(openAlgorithm.getLengthListToLoad()>closeAlgorithm.getLengthListToLoad())
			length=openAlgorithm.getLengthListToLoad();
		else
			length=closeAlgorithm.getLengthListToLoad();
		
		GregorianCalendar startDate=new GregorianCalendar();
		GregorianCalendar endDate=new GregorianCalendar();
		for(int i=0;i<length;i++){
			startDate.add(GregorianCalendar.DAY_OF_MONTH, -1);
			if(DateUtils.isWeekendDay(startDate))
				i--;
		}
		startDate.add(GregorianCalendar.DAY_OF_MONTH, -5);
		//ceny w kolejnosci od najnowszej do najstarszej
		List<FundValue> fundValueList=fundValueService.findByInvestmentFundWithDateLimit(fund, startDate, endDate);
		//ladowanie algorytmu do wygenerowania sygnalu
		for(int i=0;i<fundValueList.size();i++){
			openAlgorithm.putPrice(fundValueList.get(i));
			closeAlgorithm.putPrice(fundValueList.get(i));
		}
		
		
		//openSignal:
		if(openAlgorithm.openTransaction() && !closeAlgorithm.closeTransaction())
			return true;
		else 
			return false;
	}
	
	/** generyje sygnal zamkniecia transakcji (sprzedazy) funduszu na podstawie aktualnej ceny jednostek - metoda do decyzji o konwersji portfela */
	private boolean generateCloseSignal(InvestmentFund fund){
		SkandiaAlgorithm closeAlgorithm=skandiaAlgorithmService.findActiveCloseAlgorithm(fund);
		if(closeAlgorithm==null){
			JOptionPane.showMessageDialog(null, "fundusz "+fund.getShortName() +" nie ma ustawionego algorytmu zamykaj¹cego!", "Brak algorytmu", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		closeAlgorithm.loadAlgorithm();
		int length=closeAlgorithm.getLengthListToLoad();
		
		GregorianCalendar startDate=new GregorianCalendar();
		GregorianCalendar endDate=new GregorianCalendar();
		for(int i=0;i<length;i++){
			startDate.add(GregorianCalendar.DAY_OF_MONTH, -1);
			if(DateUtils.isWeekendDay(startDate))
				i--;
		}
		startDate.add(GregorianCalendar.DAY_OF_MONTH, -5);
		
		//ceny w kolejnosci od najnowszej do najstarszej
		List<FundValue> fundValueList=fundValueService.findByInvestmentFundWithDateLimit(fund, startDate, endDate);
		//ladowanie algorytmu do wygenerowania sygnalu
		for(int i=0;i<fundValueList.size();i++){
			closeAlgorithm.putPrice(fundValueList.get(i));
		}
		
		if(closeAlgorithm.closeTransaction())
			return true;
		else
			return false;
	}
	
	private void closeOldPortfolio(Customer user){
		CustomerPortfolio custPortfolio=user.getPortfolio();
		if(custPortfolio==null)
			return;
		
		custPortfolio.setClosingValue(user.getCapital());
		custPortfolio.setClosingDate(new GregorianCalendar());
		customerPortfolioService.saveOrUpdate(custPortfolio);
		List<PortfolioFund> portfolioFundList=portfolioFundService.findByPortfolio(custPortfolio.getPorfolio());
		for(PortfolioFund portfolioFund: portfolioFundList){
			SkandiaAlgorithm algorithm=portfolioFund.getInvestmentFund().getClosingAlgorithm();
			portfolioFund.setClosingAlgorithm(algorithm);
			FundValue actualValue=fundValueService.findByInvestmentFundIdWithAmountLimit(portfolioFund.getInvestmentFund(), 1).get(0);
			portfolioFund.setClosingValue(actualValue.getValue());
			portfolioFundService.saveOrUpdate(portfolioFund);
		}
	}
	
	private void createNewPortfolioAndSave(List<PortfolioFund> portfolioFundList, List<Customer> customerList){
		if(portfolioFundList==null || customerList==null || customerList.size()==0)
			return;
		
		Portfolio portfolio = new Portfolio();
		portfolio.setPortfolioFunds(portfolioFundList);
		portfolio.setPortfolioFunds(new ArrayList<PortfolioFund>());
		CustomerPortfolio customerPortfolio;
		portfolioService.saveOrUpdate(portfolio);
		
		
		for(PortfolioFund portfolioFund: portfolioFundList){
			portfolioFund.setPortfolio(portfolio);
			portfolioFund.setOpeningAlgorithm(portfolioFund.getInvestmentFund().getOpeningAlgorithm());
			FundValue actualValue=fundValueService.findByInvestmentFundIdWithAmountLimit(portfolioFund.getInvestmentFund(), 1).get(0);
			portfolioFund.setOpeningValue(actualValue.getValue());
		}
		portfolioFundService.saveOrUpdate(portfolioFundList);
		
		for(Customer customer: customerList){
			customerPortfolio=new CustomerPortfolio();
			GregorianCalendar openingDate=new GregorianCalendar();
			for(int i=0;i<SkandiaAlgorithManager.getDellay();i++){//przesuniecie daty otwarcia portfela o opoznienie wynikajace empirycznie z konwersji
				openingDate.add(GregorianCalendar.DAY_OF_MONTH, 1);
				if(DateUtils.isWeekendDay(openingDate))
					i--;
			}
			customerPortfolio.setOpeningDate(openingDate);
			customerPortfolio.setOpeningValue(customer.getCapital());
			customerPortfolio.addCustomer(customer);
			customerPortfolio.addPortfolio(portfolio);
			customerPortfolioService.saveOrUpdate(customerPortfolio);
			customerService.saveOrUpdate(customer);
		}
		
		
	}
	
	
	
}
