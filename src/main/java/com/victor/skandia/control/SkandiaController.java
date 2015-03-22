package com.victor.skandia.control;

import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.springframework.stereotype.Controller;

import com.victor.config.ViewControllerConfig;
import com.victor.control.ViewController;
import com.victor.entity.InvestmentFund;
import com.victor.entity.PortfolioFund;
import com.victor.skandia.model.CustomerGroupType;
import com.victor.skandia.model.SkandiaSimulationResult;
import com.victor.skandia.model.TestResult;
import com.victor.skandia.view.SkandiaPanel;

/**
 * @author Wiktor
 *
 */
@Controller
public class SkandiaController extends ViewController<SkandiaPanel> {

	private SkandiaParserController skandiaParser;
	private SkandiaAlgorithManager algorithmManager;
	private SkandiaPorfolioManager portfolioManager;
	private SkandiaChartManager chartManager;

	/**
	 * {@link SkandiaController}
	 * @param panel
	 */
	public SkandiaController(SkandiaPanel panel) {
		super(panel);
		loadControllers();
		System.out.println("SkandiaController()");
	}

	/** deleguje metode importu danych ze strony webowej do obiektu typu SkandiaParserController */
	public void importData(String startDate, String endDate, boolean isChanged) {
		if (!isChanged) {
			skandiaParser.importTodayFundPrices();
		} else if (startDate.equals(endDate)) {
			System.out.println("import one day");
			skandiaParser.importOneDayFundPrices(convert(startDate));
		} else {
			GregorianCalendar oneDate = convert(startDate);
			GregorianCalendar secondDate = convert(endDate);
			if (oneDate.before(secondDate))
				skandiaParser.importFundPricesBetween(oneDate, secondDate);
			else
				skandiaParser.importFundPricesBetween(secondDate, oneDate);
		}
	}
	
	public void createNewPortfolio(List<PortfolioFund> portfolioFundList, CustomerGroupType customerType){
//		if(skandiaParser.convertePortfolio(portfolioFundList, customerType)){
			portfolioManager.createNewPortfolio(portfolioFundList, customerType);
//		}
			
	}
	
	/** deleguje metode przeprowadzania testow algorytmow do obiektu typu SkandiaAlgorithmManager */
	public TestResult makeAlgorithmTest(InvestmentFund fundList){
		return algorithmManager.makeAlgorithmTest(fundList);
	}
	
	/** utrwala wybor algorytmow przez uzytkownika */
	public void changeChoosenAlgorithm(SkandiaSimulationResult simulationResult, InvestmentFund fund){
		algorithmManager.changeChoosenAlgorithm(simulationResult, fund);
	}
	
	/** dezaktywuje wybrane algorytmy dla funduszu */
	public void desactivateAlgorithm(InvestmentFund fund){
		algorithmManager.desactivateAlgorithm(fund);
	}
	
	public List<InvestmentFund> generateOpenTransactionSingals(){
		return portfolioManager.generateOpenTransactionSingals();
	}
	
	public List<InvestmentFund>[] checkInvestmentFundFromPortfolio(){
		return portfolioManager.checkInvestmentFundFromPortfolio();
	}
	
	/** pobiera dane ze strony o ilosci zakupionych jednostek funduszy - aktywne tylko po zmianie portfela */
	public void importPortfolioData(){
		if(skandiaParser.loadPortfolio()){
			JOptionPane.showOptionDialog(null, "Dane o portfelu zosta³y pobrane prawid³owo", "Import zakoñczony powodzeniem", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"OK"}, null);
		}else{
			JOptionPane.showOptionDialog(null, "Nie odnaleziono w³aœciwego portfela!", "Import zakoñczony niepowodzeniem", JOptionPane.YES_OPTION, JOptionPane.ERROR_MESSAGE, null, new String[]{"OK"}, null);
		}
	}
	
	public List<InvestmentFund> getActiveInvestmentFundList(){
		System.out.println(algorithmManager);
		return algorithmManager.getActiveFundList();
	}
	
	/** tworzy JPanel z wykresem liniowym dla wybranego funduszu w zakresie zaznaczonych dat, w tle pokazane sa zakresy sygnalow otwarcia i zamkniecia */
	public JPanel showChartForFundWithSignals(InvestmentFund fund, GregorianCalendar dateFrom, GregorianCalendar dateUntil){
		//TODO do odkomentowania
//		return chartManager.schowChartForFundWithSignals(fund, dateFrom, dateUntil);
		return chartManager.showChartForFundAlgorithms(fund, dateFrom, dateUntil);
	}

	/** {@link SkandiaController#skandiaParser} */
	public SkandiaParserController getSkandiaParser() {
		return skandiaParser;
	}
	
	

	/** {@link SkandiaController#skandiaParser} */
	private void loadControllers() {
		this.skandiaParser = ViewControllerConfig.skandiaParser;
		this.algorithmManager=ViewControllerConfig.algorithmManager;
		this.portfolioManager=ViewControllerConfig.skandiaPorfolioManager;
		this.chartManager=ViewControllerConfig.skandiaChartManager;
		System.out.println("setting skandiaParser: " + this.skandiaParser);
	}
	
	

	private GregorianCalendar convert(String date) {
		int day = Integer.parseInt(date.substring(0, 2));
		int month = Integer.parseInt(date.substring(3, 5));
		int year = Integer.parseInt(date.substring(6, 10));
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar;
	}

}
