package com.victor.gpw.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.victor.control.LoginController;
import com.victor.entity.Customer;
import com.victor.entity.CustomerGpwTransaction;
import com.victor.entity.GpwAlgorithm;
import com.victor.entity.GpwCompany;
import com.victor.entity.GpwTransaction;
import com.victor.entity.Quotation;
import com.victor.service.CustomerGpwTransactionService;
import com.victor.service.GpwCompanyService;
import com.victor.service.GpwTransactionService;
import com.victor.service.QuotationService;
import com.victor.utils.DateUtils;

/**
 * @author Wiktor Rosinski<br/>
 *
 */
@Controller
public class GpwPortfolioManager {
	
	@Autowired 
	private GpwCompanyService gpwCompanyService;
	@Autowired
	private QuotationService quotationService;
	@Autowired
	private GpwTransactionService gpwTransactionService;
	@Autowired
	private CustomerGpwTransactionService customerGpwTransactionService;

	/** sprawdza spolki, ktorych akcje sa kupione a nie zostaly jeszcze sprzedane i dzieli je na 3 listy spiete w tablice<br/>
	 * 0: spolki z sygnalem kupna<br/>
	 * 1: spolki z brakiem sygnalow<br/>
	 * 2: spolki z sygnalem sprzedazy<br/> */
	public List<GpwCompany>[] checkBoughtCompanies(){
		@SuppressWarnings("unchecked")
		List<GpwCompany>[] companyListTable=new List[3];
		companyListTable[0]=new ArrayList<GpwCompany>();//sygnaly otwacia
		companyListTable[1]=new ArrayList<GpwCompany>();//brak sygnalow
		companyListTable[2]=new ArrayList<GpwCompany>();//sygnaly zamkniecia
		
		Customer user=LoginController.getLoggedUser();
		List<GpwCompany> boughtCompanies = gpwCompanyService.findBounghtCompanies(user);
		for(GpwCompany company: boughtCompanies){
			if(generateOpenSignal(company)){
				companyListTable[0].add(company);
			}else if(generateCloseSignal(company)){
				companyListTable[2].add(company);
			}else{
				companyListTable[1].add(company);
			}
		}
		
		sortCompanyList(companyListTable[0]);
		sortCompanyList(companyListTable[1]);
		sortCompanyList(companyListTable[2]);
		return companyListTable;
	}
	
	/** wyszukuje sposrod wszystkich aktywnych spolek tych ktore maja wygenerowane sygnaly kupna */
	public List<GpwCompany> generateOpenTransactionSingals(){
		List<GpwCompany> companiesToOpen=new ArrayList<GpwCompany>();
		List<GpwCompany> activeCompanies=gpwCompanyService.findAllActive();
		//dla kazdej aktywnej spolki
		for(GpwCompany company:activeCompanies){
			//jezeli zostanie wygenerowany sygnal otwarcia transakcji
			if(generateOpenSignal(company))
				//dodaje do listy spolek do przekazania uzytkownikowi
				companiesToOpen.add(company);
		}
		sortCompanyList(companiesToOpen);
		return companiesToOpen;
	}
	
	/** generuje sygnal otwarcia transakcji dla kursow spolki na podstawie obecnej ceny akcji - metoda do budowania portfela */
	private boolean generateOpenSignal(GpwCompany company){
		GpwAlgorithm openAlgorithm=company.getOpeningAlgorithm();
		GpwAlgorithm closeAlgorithm=company.getClosingAlgorithm();
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
		List<Quotation> quotationList=quotationService.findByGpwCompanyWithDateLimit(company, startDate, endDate);
		//ladowanie algorytmu do wygenerowania sygnalu
		for(int i=0;i<quotationList.size();i++){
			openAlgorithm.putPrice(quotationList.get(i));
			closeAlgorithm.putPrice(quotationList.get(i));
		}
		
		//openSignal:
		if(openAlgorithm.openTransaction() && !closeAlgorithm.closeTransaction())
			return true;
		else 
			return false;
	}
	
	/** generuje sygnal zamkniecia transakcji (sprzedazy) akcji spolki na podstawie aktualnej ceny akcji - metoda do decyzji o konwersji portfela */
	private boolean generateCloseSignal(GpwCompany company){
		GpwAlgorithm closeAlgorithm=company.getClosingAlgorithm();
		if(closeAlgorithm==null){
			JOptionPane.showMessageDialog(null, "fundusz "+company.getShortName() +" nie ma ustawionego algorytmu zamykaj¹cego!", "Brak algorytmu", JOptionPane.WARNING_MESSAGE);
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
		List<Quotation> fundValueList=quotationService.findByGpwCompanyWithDateLimit(company, startDate, endDate);
		//ladowanie algorytmu do wygenerowania sygnalu
		for(int i=0;i<fundValueList.size();i++){
			closeAlgorithm.putPrice(fundValueList.get(i));
		}
		
		if(closeAlgorithm.closeTransaction())
			return true;
		else
			return false;
	}
	
	/** sortuje liste funduszy wedlug stopy zwrotu od najwyzszej do najnizszej */
	private void sortCompanyList(List<GpwCompany> companyList){
		boolean sorted =false;
		while(!sorted){
			sorted=true;
			for(int i=1;i<companyList.size();i++){
				if(companyList.get(i-1).getWeight().getRateOfProfit().compareTo(companyList.get(i).getWeight().getRateOfProfit())<0){
					GpwCompany temp=companyList.get(i);
					companyList.set(i, companyList.get(i-1));
					companyList.set(i-1, temp);
					i--;
					sorted=false;
				}
			}
		}
	}

	/**
	 * @param customerTransactionList
	 */
	public void createNewPortfolio(List<CustomerGpwTransaction> customerTransactionList) {
		GpwTransaction transaction;
		Customer user=LoginController.getLoggedUser();
		for(CustomerGpwTransaction customerTransaction: customerTransactionList){
			transaction=new GpwTransaction();
			transaction.setAuthor(user);
			transaction.setOpeningAlgorithm(customerTransaction.getCompany().getOpeningAlgorithm());
			gpwTransactionService.saveOrUpdate(transaction);
			
			customerTransaction.setOpenPrice(getActualPrice(customerTransaction.getCompany()));
			customerTransaction.setTransaction(transaction);
			customerGpwTransactionService.saveOrUpdate(customerTransaction);
		}
		
	}

	/**
	 * @param company
	 * @return
	 */
	private BigDecimal getActualPrice(GpwCompany company) {
		Quotation quotation=quotationService.findActualQuotationByCompany(company);
		return quotation.getOpenPrice();
	}
}
