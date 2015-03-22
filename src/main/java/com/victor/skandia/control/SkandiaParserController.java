package com.victor.skandia.control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.victor.control.LoginController;
import com.victor.entity.Activity;
import com.victor.entity.CapitalValue;
import com.victor.entity.Currency;
import com.victor.entity.Customer;
import com.victor.entity.FundActivity;
import com.victor.entity.FundValue;
import com.victor.entity.InvestmentFund;
import com.victor.entity.PortfolioFund;
import com.victor.service.CapitalValueService;
import com.victor.service.CurrencyService;
import com.victor.service.CustomerService;
import com.victor.service.FundActivityService;
import com.victor.service.FundValueService;
import com.victor.service.InvestmentFundService;
import com.victor.service.PortfolioFundService;
import com.victor.skandia.model.CustomerGroupType;
import com.victor.utils.DataConverter;
import com.victor.utils.DataUtils;
import com.victor.utils.DateFormat;
import com.victor.utils.DateUtils;

/**
 * @author Wiktor
 *
 */
@Controller
public class SkandiaParserController {

	/** komentarze */
	private WebDriver driver;
	private String baseUrl;
	@SuppressWarnings("unused")
	private boolean acceptNextAlert = true;


	private List<String> currencyList;
	private List<Integer> riskList;
	private List<String> fullNamesList;
//	private Map<String, InvestmentFund> fundMap = null;
//	private List<InvestmentFund> investmentFundList = null;

	private int restart = 0;
	private boolean loaded;

	@Autowired
	private InvestmentFundService investmentFundService;
	@Autowired
	private FundActivityService fundActivityService;
	@Autowired
	private FundValueService fundValueService;
	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private PortfolioFundService portfolioFundService;
	@Autowired
	private CapitalValueService capitalValueService;
	@Autowired
	private CustomerService customerService;

	/**
	 * {@link SkandiaParserController}
	 */
	public SkandiaParserController() {
		System.out.println("SkandiaParser()");
	}

	/**
	 * @author Wiktor typ do przechowywania danych ze strony, zanim zastana
	 *         przepisane na wlasciwy typ do zapisu na baze danych
	 */

	// *****************************************************************
	// ********************** PUBLIC ***********************************
	// *****************************************************************

	/**
	 * Importuje ceny jednostek fundyszy inwestycyjnych dla obecnego dnia
	 */
	public void importTodayFundPrices() {

		loaded = false;
		openToImport();
		GregorianCalendar date = new GregorianCalendar();
		String stringDate = DateUtils.convertToStringFormat(date,
				DateFormat.YYYY_MM_DD);
		try {
			loadPrices(date);
			printReport("| " + stringDate + " |            |\n");
			printReport("+-------------------------+\n");
		} catch (Exception e) {
			if (loaded) {
				printReport("| " + stringDate + " |            |\n");
				printReport("+-------------------------+\n");
			} else {
				printReport("|            | " + stringDate + " |\n");
				printReport("+-------------------------+\n");
			}
		}
		close();
	}

	/**
	 * Importuje cen jednostek funduszy inwestycyjnych od dnia startDate do dnia
	 * obecnego
	 * 
	 * @param startDate
	 *            data od ktorej rozpoczyna sie import cen jednostek funduszy
	 */
	public void importOneDayFundPrices(GregorianCalendar date) {
		loaded = false;
		openToImport();
		String stringDate = DateUtils.convertToStringFormat(date,
				DateFormat.YYYY_MM_DD);
		try {
			setDate(date);
			loadPrices(date);
			printReport("| " + stringDate + " |            |\n");
			printReport("+-------------------------+\n");
		} catch (Exception e) {
			if (loaded) {
				printReport("| " + stringDate + " |            |\n");
				printReport("+-------------------------+\n");
			} else {
				printReport("|            | " + stringDate + " |\n");
				printReport("+-------------------------+\n");
			}
		}
		close();
	}

	/**
	 * Import cen jednostek funduszy inwestycyjnych od dnia startDate do dnia
	 * endDate
	 * 
	 * @param startDate
	 *            data od ktorej zaczyna sie import cen jednostek funduszy
	 * @param endDate
	 *            data do ktorej wlacznie wykonany zostanie import cen jednostek
	 *            funduszy
	 */
	public void importFundPricesBetween(GregorianCalendar startDate,
			GregorianCalendar endDate) {
		System.out.println("import beetween "+DateUtils.convertToStringFormat(startDate, DateFormat.DD_MM_YYYY)+" - "+DateUtils.convertToStringFormat(endDate, DateFormat.DD_MM_YYYY));
		loaded = false;
		int result = startDate.compareTo(endDate);
		int numberOfDaysBetween = DateUtils.numberDaysBetween(startDate,
				endDate);
		GregorianCalendar parsingDate;
		if (result <= 0) {
			parsingDate = new GregorianCalendar(DateUtils.getYear(startDate),
					DateUtils.getMonth(startDate) - 1,
					DateUtils.getDayOfMonth(startDate));
		} else {
			parsingDate = new GregorianCalendar(DateUtils.getYear(endDate),
					DateUtils.getMonth(endDate) - 1,
					DateUtils.getDayOfMonth(endDate));
		}

		openToImport();
		String stringDate = DateUtils.convertToStringFormat(parsingDate,
				DateFormat.YYYY_MM_DD);
		restart = 0;
		 try {
		setDate(parsingDate);
		loadPrices(parsingDate);
		printReport("| " + stringDate + " |            |\n");
		printReport("+-------------------------+\n");

		 } catch (Exception e) {
		 if(loaded){
		 printReport("| "+stringDate+" |            |\n");
		 printReport("+-------------------------+\n");
		 } else {
		 printReport("|            | "+stringDate+" |\n");
		 printReport("+-------------------------+\n");
		 }
		 }

		for (int i = 0; i < numberOfDaysBetween; i++) {
			parsingDate.add(GregorianCalendar.DAY_OF_MONTH, 1);
			System.out.println(">>>" + i);
			if (!DateUtils.isWeekendDay(parsingDate)) {
				stringDate = DateUtils.convertToStringFormat(parsingDate,
						DateFormat.YYYY_MM_DD);
				 try {
				loaded = false;
				restart = 0;
				setDate(parsingDate);
				check(parsingDate);
				loadPrices(parsingDate);
				printReport("| " + stringDate + " |            |\n");
				printReport("+-------------------------+\n");

				 } catch (Exception e) {
				 if(loaded){
				 printReport("| "+stringDate+" |            |\n");
				 printReport("+-------------------------+\n");
				 } else {
				 printReport("|            | "+stringDate+" |\n");
				 printReport("+-------------------------+\n");
				 }
				 }
			}
		}
		close();
	}
	
	/** pobiera dane o portfelu - liczby zakupionych jednostek */
	public boolean loadPortfolio(){
		//TODO
		openToLogin();
		login(LoginController.getLoggedUser().getSkandiaLogin(), LoginController.getLoggedUser().getSkandiaPassword());
		goToPortfolioComposition();
		Map<String, BigDecimal> fundShareMap=loadPortfolioCompositionData();
		Customer user=LoginController.getLoggedUser();
		if(user.getPortfolio()==null){
			close();
			return false;
		}
		
		List<PortfolioFund> portfolioFundList=portfolioFundService.findByPortfolio(user.getPortfolio().getPorfolio());
		boolean settingSuccess;
		for(String fundName: fundShareMap.keySet()){
			settingSuccess=false;
			for(PortfolioFund portfolioFund: portfolioFundList){
				System.out.println(fundName+"\t"+portfolioFund.getInvestmentFund().getShortName()+"\t"+portfolioFund.getInvestmentFund().getShortName().replaceAll("\\s+","").equals(fundName));
				if(portfolioFund.getInvestmentFund().getShortName().replaceAll("\\s+","").equals(fundName)){
					portfolioFund.setUnitAmount(fundShareMap.get(fundName));
					System.out.println("*****");
					settingSuccess=true;
				}
			}
			if(!settingSuccess){
				close();
				return false;
			}
		}
		portfolioFundService.saveOrUpdate(portfolioFundList);
		BigDecimal capital=loadCapitalFromPortfolio();
		if(!capitalValueService.isTodayCapital(LoginController.getLoggedUser())){
			System.out.println("save new capitalValue");
			CapitalValue capitalValue=new CapitalValue();
			capitalValue.setCustomer(user);
			GregorianCalendar today=new GregorianCalendar();
			DateUtils.setToStartDate(today);
			capitalValue.setDate(today);
			capitalValue.setValue(capital);
			capitalValueService.saveOrUpdate(capitalValue);
			user.setCapital(capital);
			customerService.saveOrUpdate(user);
		}

		close();
		return true;
	}
	
	public boolean convertePortfolio(List<PortfolioFund> portfolioFundList, CustomerGroupType customerType){
		List<Customer> customerList=DataUtils.getCustomers(customerType, customerService);
		if(customerList==null || customerList.size()==0)
			return false;
		openToLogin();
		boolean result=true;
		for(Customer customer: customerList){
			login(customer.getSkandiaLogin(), customer.getSkandiaPassword());
			goToConvertePortfolio();
			convertePortfolio(portfolioFundList, customer);
			confirmPortfolioConversion();
		}
		close();
		return result;
	}
	
	private void confirmPortfolioConversion(){
		driver.findElement(By.id("form:j_id413")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/div[2]/form/span[1]/a[3]")).click();
	}


	// *****************************************************************
	// ********************** PRIVATE **********************************
	// *****************************************************************

	/**
	 * otwiera strone <a href=
	 * "http://www.skandia.pl/centrum-inwestycyjne/notowania-funduszy.html"
	 * >skandia/notowania-funduszy</a>
	 */
	private void openToImport()  {
		if (driver == null) {
			try {
				openDriver();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Dodaj do projektu aktualna bibliotekê Selenium", "Nieaktualna biblioteka Selenium", JOptionPane.ERROR_MESSAGE);
			}
		}
		printReport("\n\n");
		printReport("+-------------------------+\n");
		printReport("|  pobrano   | blad pobr. |\n");
		printReport("+-------------------------+\n");
	}
	
	private void openDriver() {
		baseUrl = "http://www.skandia.pl/centrum-inwestycyjne/notowania-funduszy.html";
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl);
	}
	
	private void openToLogin(){
		if (driver == null) {
			baseUrl = "https://online.skandia.pl/sol/app/login.jsf";
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(baseUrl);
		}
	}

	private void close() {
		driver.close();
	}

	/**
	 * pobiera nazwy funduszy ze strony
	 * 
	 * @return lista nazw funduszy investycyjnych
	 */
	private List<String> getNames() {
		List<String> fundNamesList = new ArrayList<String>();
		List<WebElement> elements = driver
				.findElements(By.className("tooltip"));
		// sprawdza kazdy element, gdy posiada tag <a> pobiera nazwe funduszu
		for (WebElement element : elements) {
			String tagName = element.getTagName();
			if (tagName != null && tagName.equals("a")) {
				fundNamesList.add(element.getText());
				System.out.println(element.getText());
			}
		}
		return fundNamesList;
	}

	/**
	 * pobiera ceny funduszy ze strony
	 * 
	 * @return lista cen funduszy jako BigDecimal
	 */
	private List<BigDecimal> getPrices() {
		List<BigDecimal> fundPriceList = new ArrayList<BigDecimal>();
		List<WebElement> priceElements = driver.findElements(By
				.className("price"));
		// sprawdza kazdy element, gdy posiada tag <td> pobiera cene funduszu,
		// gdy String jest pusty ustawia 0
		for (WebElement element : priceElements) {
			if (element.getTagName().equals("td")) {
				String price = element.getText();
				if (price.indexOf(".") > 0) {
					if (price.indexOf(" / ") > 0)
						price = price.substring(0, price.indexOf(" /"));
					// System.out.println(price);
					fundPriceList.add(DataConverter.stringToBigDecimal(price,
							"."));
				}
			}
		}
		return fundPriceList;
	}

	/**
	 * pobiera wartosci ryzyka dla funduszy ze strony
	 * 
	 * @return lsita wartosci rysyka dla funduszy
	 */
	private void getRisks() {
		List<Integer> fundRiskList = new ArrayList<Integer>();
		List<WebElement> riskElements = driver.findElements(By
				.className("risk"));
		// sprawdza kazdy element, gdy posiada tag <span> pobiera walute
		// funduszu
		for (WebElement element : riskElements) {
			if (element.getTagName().equals("span")) {
				if (element.getText().equals("")) {
					fundRiskList.add(0);
				} else {
					fundRiskList.add(new Integer(element.getText()));
				}
				// System.out.println(fundRiskList.get(fundRiskList.size()-1));
			}
		}
		this.riskList = fundRiskList;
	}

	/**
	 * Pobiera waluty funduszy
	 * 
	 * @return lista walut funduszy
	 */
	private void getCurrencies() {
		List<String> fundCurrencyList = new ArrayList<String>();
		List<WebElement> currencyElements = driver.findElements(By
				.className("currency"));
		for (WebElement element : currencyElements) {
			if (element.getTagName().equals("td")) {
				fundCurrencyList.add(element.getText());
			}
		}
		this.currencyList = fundCurrencyList;
	}

	private List<GregorianCalendar> getDates() {
		List<GregorianCalendar> fundDateList = new ArrayList<GregorianCalendar>();
		List<WebElement> currencyElements = driver.findElements(By
				.className("date"));
		for (WebElement element : currencyElements) {
			if (element.getTagName().equals("td")) {
				String stringDate = element.getText();
				GregorianCalendar date = DateUtils.convertToDate(stringDate);
				if (date != null)
					fundDateList.add(date);
			}
		}
		return fundDateList;
	}

	private void getFullNames() {
		List<String> fullNamesList = new ArrayList<String>();
		List<WebElement> currencyElements = driver.findElements(By
				.className("tooltip"));
		int start;
		int stop;
		for (WebElement element : currencyElements) {
			try {
				String name = element.getAttribute("txt");
				start = name.indexOf(">") + 1;
				stop = name.indexOf("</");
				name = name.substring(start, stop);
				name.trim();
				fullNamesList.add(name);
			} catch (Exception e) {
				continue;
			}
		}
		this.fullNamesList = fullNamesList;
	}

	/**
	 * tworzy mape obiektow TempFundData sluzaca do ladowania danych na wlasciwe
	 * obiekty
	 * 
	 * @param fundNames
	 *            lista nazw funduszy
	 * @param fundCurrencies
	 *            lsita cen jednnstek funduszy
	 * @return mapa key: nazwa funduszu, value: TempFundData
	 */
	@SuppressWarnings("unused")
	private Map<String, TempFundData> createTempFundDataMap(
			List<String> fundNames, List<BigDecimal> fundPrices,
			GregorianCalendar date) {
		Map<String, TempFundData> tempFundDataMap = new HashMap<String, SkandiaParserController.TempFundData>();
		for (int i = 0; i < fundNames.size(); i++) {
			TempFundData tempFundData = new TempFundData();
			tempFundData.setName(fundNames.get(i));
			tempFundData.setPrice(fundPrices.get(i));
			tempFundData.setListindex(i);
			tempFundData.setDate(date);
			tempFundDataMap.put(fundNames.get(i), tempFundData);
		}

		return tempFundDataMap;
	}

	private List<TempFundData> createTempFundDateList(List<String> fundNames,
			List<BigDecimal> fundPrices, List<GregorianCalendar> fundDateList) {
		List<TempFundData> tfdList = new ArrayList<SkandiaParserController.TempFundData>();
		for (int i = 0; i < fundNames.size(); i++) {
			TempFundData tempFundData = new TempFundData();
			tempFundData.setName(fundNames.get(i));
			tempFundData.setPrice(fundPrices.get(i));
			tempFundData.setListindex(i);
			tempFundData.setDate(fundDateList.get(i));
			tfdList.add(tempFundData);
		}
		return tfdList;

	}

	/**
	 * pobiera dane ze strony i zapisuje do bazy danych wraz z cala obsluga
	 * niekatywnych i nieobecnych w bazie danych funduszy
	 */

	private void loadPrices(GregorianCalendar date) {
		System.out.println("pobiera dane ze strony");
		List<String> fundNames = null;
		try {
			fundNames = getNames();

			List<BigDecimal> fundPrices = getPrices();
			List<GregorianCalendar> fundDateList = getDates();
			List<TempFundData> tempFundDataList = createTempFundDateList(fundNames, fundPrices, fundDateList);
//			if (fundMap == null) {
				List<InvestmentFund> investmentFundList = investmentFundService.findAllActive();
				Map<String, InvestmentFund> fundMap = createFundMap(investmentFundList);
//			}

			for (TempFundData tempFundData : tempFundDataList) {
				InvestmentFund fund = fundMap.get(tempFundData.getName());
				if (fund != null) {
					addPriceToFund(tempFundData, fund);
//					fundMap.remove(tempFundData.getName());//usuwa fundusz z mapy
				} else {
					if (currencyList == null) {
						getCurrencies();
						getRisks();
						getFullNames();
					}
					int i = tempFundData.getListindex();
					tempFundData.setCurrency(currencyList.get(i));
					tempFundData.setRisk(riskList.get(i));
					tempFundData.setFullName(fullNamesList.get(i));
					InvestmentFund newFund = createInvestmentFund(tempFundData);
					investmentFundList.add(newFund);
					fundMap.put(newFund.getShortName(), newFund);
					investmentFundService.saveOrUpdate(newFund);
				}
			}
//			InvestmentFund fund;
//			for(String key: fundMap.keySet()){
//				fund=fundMap.get(key);
//				fund.setActive(false);
//				investmentFundService.saveOrUpdate(fund);
//			}

			loaded = true;
		} catch (Exception e) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				throw new RuntimeException(e1);
			}
			restart++;
			System.err.println(">>>> RESTART " + restart + " <<<<");

			if (restart > 3)
				throw new RuntimeException();
			loadPrices(date);
		}
	}

	private Map<String, InvestmentFund> createFundMap(
			List<InvestmentFund> fundList) {
		Map<String, InvestmentFund> fundMap = new HashMap<String, InvestmentFund>();
		for (InvestmentFund fund : fundList) {
			fundMap.put(fund.getShortName(), fund);
		}
		return fundMap;
	}

	/**
	 * Dodaje wartosc jednostek funduszu do funduszu. Tworzy obiekt FundValue i
	 * przypisuje wzajemna relacje
	 */
	private void addPriceToFund(TempFundData tempFundData, InvestmentFund fund) {
		boolean isSaved = fundValueService.isFundValueInDate(
				tempFundData.getDate(), fund);
		if (!isSaved) {
			FundValue fundValue = new FundValue();
			fundValue.setDate(tempFundData.date);
			fundValue.setValue(tempFundData.price);
			fundValue.setInvestmentFund(fund);
			fund.getValueHistory().add(fundValue);
			fundValueService.saveOrUpdate(fundValue);
		}
		tempFundData.setUsedToSave(true);
	}

	/**
	 * proces tworzenia kompletnego funduszu ze wszustkimi obiektami w skal
	 * niego wchodzacymi wraz z utworzeniem FundValue - aktualna cena
	 */
	private InvestmentFund createInvestmentFund(TempFundData tempFundData) {
		InvestmentFund investmentFund = new InvestmentFund();
		investmentFund.setShortName(tempFundData.getName());
		investmentFund.setActive(true);
		investmentFund.setRisk(tempFundData.getRisk());
		investmentFund.setFullName(tempFundData.getFullName());
		investmentFund.setValueHistory(new ArrayList<FundValue>());
		investmentFund.setFundActivity(new ArrayList<FundActivity>());
		investmentFund.setPortfolioHistory(new ArrayList<PortfolioFund>());

		// System.out.println(currencyService.findAll());
		Currency currency = currencyService.findByName(tempFundData
				.getCurrency());
		if (currency == null) {
			currency = new Currency();
			currency.setName(tempFundData.getCurrency());
			currency.setInvestmentFunds(new ArrayList<InvestmentFund>());
			currencyService.saveOrUpdate(currency);
		}
		currency.getInvestmentFunds().add(investmentFund);
		investmentFund.setCurrency(currency);

		FundActivity fundActivity = new FundActivity();
		Activity activity = new Activity();
		activity.setBeginActivityDate(tempFundData.getDate());
		fundActivity.setActivity(activity);
		fundActivity.setFund(investmentFund);
		investmentFund.getFundActivity().add(fundActivity);
		investmentFundService.saveOrUpdate(investmentFund);
		addPriceToFund(tempFundData, investmentFund);

		return investmentFund;
	}

	@SuppressWarnings("unused")
	private String convertDate(String date) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(date.substring(6, 10)).append("-");
		buffer.append(date.substring(3, 5)).append("-");
		buffer.append(date.substring(0, 2));
		return buffer.toString();
	}
	
	private Map<String, BigDecimal> loadPortfolioCompositionData(){
		Map<String, BigDecimal> portfolioComposition= new HashMap<String, BigDecimal>();
		String fundNamePart1="/html/body/div[2]/div[1]/div[2]/div[2]/form/span[1]/table[1]/tbody/tr[";
		String fundNamePart2="]/td[1]/div/span[2]";
		String fundUnitPart1="/html/body/div[2]/div[1]/div[2]/div[2]/form/span[1]/table[1]/tbody/tr[";
		String fundUnitPart2="]/td[2]";
		StringBuffer buffer;
		String fundName;
		String sFundUnit;
		BigDecimal fundUnit;
		
		for(int i=1; true; i++){
			try{
				buffer=new StringBuffer();
				buffer.append(fundNamePart1).append(i).append(fundNamePart2);
				fundName=driver.findElement(By.xpath(buffer.toString())).getText();
				System.out.print(fundName+"\t");
				
				buffer=new StringBuffer();
				buffer.append(fundUnitPart1).append(i).append(fundUnitPart2);
				sFundUnit=driver.findElement(By.xpath(buffer.toString())).getText();
				sFundUnit=sFundUnit.replace(',', '.').trim();
				fundUnit=new BigDecimal(sFundUnit);
				System.out.println(sFundUnit);
				
				portfolioComposition.put(fundName, fundUnit);
			}catch(Exception e){
				break;
			}
		}
		 
		return portfolioComposition;
	}
	
	/** pobiera wartosc kapitalu z widoku portfela */
	private BigDecimal loadCapitalFromPortfolio(){
		String sCapital=driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/div[2]/form/div/table[1]/tbody/tr/td[1]/table/tbody/tr/td[2]/span")).getText();
		sCapital=sCapital.replace(',', '.').replaceAll("\\s+","");
		sCapital=sCapital.substring(0,sCapital.indexOf("PLN"));
		System.out.println(sCapital);
		BigDecimal capital=new BigDecimal(sCapital);
		System.out.println(capital);
		return capital;
	}
	
	private boolean convertePortfolio(List<PortfolioFund> portfolioFundList, Customer customer){
		boolean result=true;
		WebElement selectedOption;
		for(PortfolioFund portfolioFund: portfolioFundList){
//			driver.findElement(By.id("form:fundSelect")).sendKeys(portfolioFund.getInvestmentFund().getShortName().replaceAll("\\s+",""));
			System.out.println("FUND: "+portfolioFund.getInvestmentFund().getShortName());
			selectedOption=null;
			List<WebElement> optionList=driver.findElements(By.tagName("option"));
			for(WebElement option: optionList){
				System.out.println(option.getText());
				if(option.getText().indexOf(portfolioFund.getInvestmentFund().getShortName().replaceAll("\\s+",""))>=0){
					System.out.println("found "+portfolioFund.getInvestmentFund().getShortName());
					selectedOption=option;
					break;
				}
			}
			if(selectedOption!=null){
				selectedOption.click();
				driver.findElement(By.id("form:share")).click();
				driver.findElement(By.id("form:share")).clear();
				driver.findElement(By.id("form:share")).sendKeys(Integer.toString(portfolioFund.getPercentage().intValue()));
				driver.findElement(By.id("form:fs_sub")).click();
				optionList.remove(selectedOption);
			}else{
				JOptionPane.showMessageDialog(null, portfolioFund.getInvestmentFund().getShortName()+" jest niedostepny dla uzytkownika "+customer.getLogin(), "Niedostêpny fundusz", JOptionPane.WARNING_MESSAGE);
				result=false;
			}

			
		}
		return result;
		
		//TODO implementacja konwersji portfela
	}
	


	// *****************************************************************
	// ********************** NAWIGACJA ********************************
	// *****************************************************************

	/** ustawia date na przeglarce, dla ktorej maja byc parsowane ceny */
	private void setDate(GregorianCalendar date) {
		System.out.println("set date: "
				+ DateUtils.convertToStringFormat(date, DateFormat.YYYY_MM_DD));
		driver.findElement(By.id("quotesDate")).click();
		driver.findElement(By.id("quotesDate")).clear();
		driver.findElement(By.id("quotesDate")).sendKeys(
				DateUtils.convertToStringFormat(date, DateFormat.YYYY_MM_DD));
		driver.findElement(By.id("quotesDate")).click();
		driver.findElement(By.id("quotesDate")).submit();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

	}

	private void check(GregorianCalendar date) {
		String stringDate = DateUtils.convertToStringFormat(date,
				DateFormat.YYYY_MM_DD);
		System.out.println(stringDate);
		String parsedDate = driver.findElement(By.id("quotesDate"))
				.getAttribute("value");
		System.out.println(parsedDate);
		if (stringDate.equals(parsedDate)) {
			return;
		} else {
			setDate(date);
		}
	}
	
	private void login(String login, String password){
		driver.findElement(By.id("j_username")).click();
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys(login);
	
		driver.findElement(By.name("j_password")).click();
		driver.findElement(By.name("j_password")).clear();
		driver.findElement(By.name("j_password")).sendKeys(password);
		
		driver.findElement(By.id("skandiaLoginButton")).click();
	}
	
	private void goToPortfolioComposition(){
		driver.findElement(By.id("form:contractsList:0:goContract")).click();
		driver.findElement(By.id("menuForm:evaluation")).click();
	}

	private void goToConvertePortfolio(){
		driver.findElement(By.id("form:contractsList:0:goContract")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/div[1]/form/div[8]/div[2]/div[2]/ul/li[3]/a")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/div[2]/form/span[1]/a[1]")).click();
		driver.findElement(By.id("form:j_id343")).click();
		driver.findElement(By.name("form:j_id407")).click();
	}
	
	public void test() {
		List<WebElement> elements = driver
				.findElements(By.className("tooltip"));
		List<WebElement> priceElements = driver.findElements(By
				.className("price"));
		List<WebElement> currencyElements = driver.findElements(By
				.className("currency"));
		List<WebElement> riskElements = driver.findElements(By
				.className("risk"));

		List<String> names = new ArrayList<String>();
		List<String> prices = new ArrayList<String>();
		List<String> currences = new ArrayList<String>();
		List<String> risks = new ArrayList<String>();

		for (WebElement element : elements) {
			if (element.getTagName().equals("a"))
				names.add(element.getText());

			// System.out.println(element.getText());
			// System.out.println(element.getTagName());
			// System.out.println(element.getAttribute("span"));
			// System.out.println("*****************************");
		}
		for (WebElement element : priceElements) {
			if (element.getTagName().equals("td"))
				prices.add(element.getText());
		}
		for (WebElement element : riskElements) {
			if (element.getTagName().equals("span"))
				risks.add(element.getText());
		}
		for (WebElement element : currencyElements) {
			// if(element.getTagName().equals("span"))
			currences.add(element.getText());
		}
		System.out.println("names size:\t" + names.size());
		System.out.println("risks size:\t" + risks.size());
		System.out.println("prices size:\t" + prices.size());
		System.out.println("currences size:\t" + currences.size());
		for (int i = 0; i < names.size(); i++) {
			System.out.print(names.get(i) + "\t");
			System.out.print(risks.get(i) + "\t");
			System.out.print(prices.get(i) + "\t");
			System.out.print(currences.get(i) + "\t");
			System.out.println();
		}

	}

	private void printReport(String text) {
		GregorianCalendar date = new GregorianCalendar();
		String fileName = DateUtils.convertToStringFormat(date,
				DateFormat.YYYY_MM_DD);
		try {
			File file =new File("configs/reports/skandia/import_reports");
			file.mkdirs();
			FileWriter writer = new FileWriter("configs/reports/skandia/import_reports/" + fileName
					+ ".log", true);
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	


	class TempFundData {
		/** nazwa funduszu */
		private String name;
		/** pelna nazwa funduszu */
		private String fullName;
		/** waluta funduszu */
		private String currency;
		/** poziom ryzyka funduszu */
		private int risk;
		/** wartosc jednostki funduszu */
		private BigDecimal price;
		/**
		 * true - gdy dane przepisane do InvestmentFund, flase - gdy funduszu
		 * nie znaleziono, lub jest nieaktywny
		 */
		private boolean usedToSave;
		/** data wyceny funduszu - powiazane z pole 'price' */
		private GregorianCalendar date;
		/** index na liscie wg metody getNames() */
		private int listindex;

		/** {@link SkandiaParserController.TempFundData} */
		public TempFundData() {
			usedToSave = false;
		}

		/** {@link SkandiaParserController.TempFundData#currency} */
		public String getCurrency() {
			return currency;
		}

		/** {@link SkandiaParserController.TempFundData#name} */
		public String getName() {
			return name;
		}

		/** {@link SkandiaParserController.TempFundData#price} */
		public BigDecimal getPrice() {
			return price;
		}

		/** {@link SkandiaParserController.TempFundData#risk} */
		public int getRisk() {
			return risk;
		}

		/** {@link SkandiaParserController.TempFundData#currency} */
		public void setCurrency(String currency) {
			this.currency = currency;
		}

		/** {@link SkandiaParserController.TempFundData#name} */
		public void setName(String name) {
			this.name = name;
		}

		/** {@link SkandiaParserController.TempFundData#price} */
		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		/** {@link SkandiaParserController.TempFundData#risk} */
		public void setRisk(int risk) {
			this.risk = risk;
		}

		/** {@link SkandiaParserController.TempFundData#usedToSave} */
		public boolean isUsedToSave() {
			return usedToSave;
		}

		/** {@link SkandiaParserController.TempFundData#usedToSave} */
		public void setUsedToSave(boolean usedToSave) {
			this.usedToSave = usedToSave;
		}

		/** {@link SkandiaParserController.TempFundData#date} */
		public GregorianCalendar getDate() {
			return date;
		}

		/** {@link SkandiaParserController.TempFundData#date} */
		public void setDate(GregorianCalendar date) {
			this.date = date;
		}

		/** {@link SkandiaParserController.TempFundData#listindex} */
		public int getListindex() {
			return listindex;
		}

		/** {@link SkandiaParserController.TempFundData#listindex} */
		public void setListindex(int listindex) {
			this.listindex = listindex;
		}

		/** {@link SkandiaParserController.TempFundData#fullName} */
		public String getFullName() {
			return fullName;
		}

		/** {@link SkandiaParserController.TempFundData#fullName} */
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
	}
	
	// *****************************************************************
	// ***************** GETTERS & SETTERS *****************************
	// *****************************************************************

	/** {@link SkandiaParserController#investmentFundService} */
	public InvestmentFundService getInvestmentFundService() {
		return investmentFundService;
	}

	/** {@link SkandiaParserController#investmentFundService} */
	public void setInvestmentFundService(
			InvestmentFundService investmentFundService) {
		this.investmentFundService = investmentFundService;
	}

	/** {@link SkandiaParserController#fundActivityService} */
	public FundActivityService getFundActivityService() {
		return fundActivityService;
	}

	/** {@link SkandiaParserController#fundActivityService} */
	public void setFundActivityService(FundActivityService fundActivityService) {
		this.fundActivityService = fundActivityService;
	}

	/** {@link SkandiaParserController#fundValueService} */
	public FundValueService getFundValueService() {
		return fundValueService;
	}

	/** {@link SkandiaParserController#fundValueService} */
	public void setFundValueService(FundValueService fundValueService) {
		this.fundValueService = fundValueService;
	}

	/** {@link SkandiaParserController#currencyService} */
	public CurrencyService getCurrencyService() {
		return currencyService;
	}

	/** {@link SkandiaParserController#currencyService} */
	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

}
