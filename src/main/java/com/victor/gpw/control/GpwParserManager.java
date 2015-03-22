package com.victor.gpw.control;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.victor.entity.Currency;
import com.victor.entity.GpwCompany;
import com.victor.entity.Quotation;
import com.victor.service.CurrencyService;
import com.victor.service.GpwCompanyService;
import com.victor.service.QuotationService;
import com.victor.utils.DateFormat;
import com.victor.utils.DateUtils;

/**
 * @author Wiktor Rosinski
 *
 */
@Controller
public class GpwParserManager {
	
	@Autowired
	private GpwCompanyService gpwCompanyService;
	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private QuotationService quotationService;
	

	private static String quotationXPath1="/html/body/div[2]/table/tbody/tr[";
	private static String comapnyDataXPath="/html/body/div[2]/div[3]/div[15]/table/tbody/tr[";
	/** komentarze */
	private WebDriver driver;
	private String baseUrl;
	@SuppressWarnings("unused")
	private boolean acceptNextAlert = true;
	
	private Map<String, GpwCompany> companyMap;
	private List<GpwCompany> companyListToComplete;
	private Integer indexToLoad=null;

	/**
	 * Importuje cen jednostek funduszy inwestycyjnych od dnia startDate do dnia
	 * obecnego
	 * 
	 * @param startDate
	 *            data od ktorej rozpoczyna sie import cen jednostek funduszy
	 */
	public void importOneDayCompanyQuotation(GregorianCalendar date) {
		String stringDate = DateUtils.convertToStringFormat(date,
				DateFormat.YYYY_MM_DD);
		openToImport(stringDate);
		
		//TODO odkomentowac
//		loadCompanyMap();
//		try{
//			loadPrices(stringDate);
//			printReport("| " + stringDate + " |            |\n");
//			printReport("+-------------------------+\n");
//		}catch(Exception e){
//			printReport("|            | " + stringDate + " |\n");
//			printReport("+-------------------------+\n");
//		}
		activateCompanies();
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
	public void importCompanyQuotationBetween(GregorianCalendar startDate,
			GregorianCalendar endDate) {
		GregorianCalendar calendar=new GregorianCalendar(startDate.get(GregorianCalendar.YEAR), startDate.get(GregorianCalendar.MONTH), startDate.get(GregorianCalendar.DAY_OF_MONTH));
		while(DateUtils.isWeekendDay(calendar)){
			calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
		}
		String stringDate = DateUtils.convertToStringFormat(calendar,
				DateFormat.YYYY_MM_DD);
		openToImport(stringDate);
		loadCompanyMap();
		int days=DateUtils.numberDaysBetween(startDate, endDate);
		int loadId=0;
		
		for(int i=0;i<days;i++){
			if(i==days-1){
				if(new GregorianCalendar().get(GregorianCalendar.HOUR_OF_DAY)<18)
					break;
			}
//			try{
				loadPrices(stringDate);
				printReport("| " + stringDate + " |            |\n");
				printReport("+-------------------------+\n");
//			}catch(Exception e){
//				if(loadId>3){
//					printReport("|            | " + stringDate + " |\n");
//					printReport("+-------------------------+\n");
//					close();
//				}
//				loadId++;
//				i--;
//				continue;
//			}
			calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
			stringDate=DateUtils.convertToStringFormat(calendar, DateFormat.YYYY_MM_DD);
			setDate(stringDate);
		}
		activateCompanies();
	}
	

	// *****************************************************************
	// ********************** PRIVATE **********************************
	// *****************************************************************
	
	private void loadCompanyMap(){
		companyListToComplete=new ArrayList<GpwCompany>();
		List<GpwCompany> companyList=gpwCompanyService.findAllActive();
		companyMap=new HashMap<String, GpwCompany>();
		for(GpwCompany company: companyList){
			companyMap.put(company.getName(), company);
		}
	}
	
	
	
	/** towrzy nowa spolke i zapisuje ja wraz z waluta, nie zapisuje pelnej nazwy spolki. Pelna nazwa jest na innej stronie. */
	private GpwCompany createNewCompany(ImportData data){
		GpwCompany company=new GpwCompany();
		company.setActive(true);
		company.setIsin(data.isin);
		company.setName(data.name);
		company.setQuotationHistory(new ArrayList<Quotation>());

		gpwCompanyService.saveOrUpdate(company);
		companyMap.put(company.getName(), company);
		companyListToComplete.add(company);
		return company;
	}
	
	/**
	 * otwiera strone <a href=
	 * "http://www.skandia.pl/centrum-inwestycyjne/notowania-funduszy.html"
	 * >skandia/notowania-funduszy</a>
	 */
	private void openToImport(String date) {
		if (driver == null) {
			try{
				openDriver(date);
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Dodaj do projektu aktualna bibliotekê Selenium", "Nieaktualna biblioteka Selenium", JOptionPane.ERROR_MESSAGE);
			}
		}
		printReport("\n\n");
		printReport("+-------------------------+\n");
		printReport("|  pobrano   | blad pobr. |\n");
		printReport("+-------------------------+\n");
	}
	
	private void openDriver(String date){
		baseUrl = "http://www.gpw.pl/notowania_archiwalne_full?type=10&date="+date;
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl);
	}
	
	private void setDate(String date){
		baseUrl = "http://www.gpw.pl/notowania_archiwalne_full?type=10&date="+date;
		driver.get(baseUrl);
	}
	
	private void redirectToLoadCompanyData(){
		baseUrl = "http://www.gpw.pl/akcje_i_pda_notowania_ciagle_pelna_wersja#all";
		driver.get(baseUrl);
	}
	
	private void loadPrices(String stringDate){
		if(!checkIsWorkDay())
			return;
		List<ImportData> dataList=loadData();
		List<Quotation> quotationList=new ArrayList<Quotation>();
		GpwCompany company;
		Quotation quotation;
		for(ImportData data: dataList){
			company=companyMap.get(data.name);
			if(company==null)//gdy nie ma stworzonej jeszcze spolki
				company=createNewCompany(data);
//			if(company.getShortName()==null)
//				companyListToComplete.add(company);
			if(quotationService.isQuotationForCompanyInDate(company, stringDate, DateFormat.YYYY_MM_DD))//gdy juz notowania dla danego dnia zostaly zaimportowane
				continue;
			
			quotation=new Quotation();
			quotation.setCompany(company);
			quotation.setDate(DateUtils.convertToDateFormat(stringDate, DateFormat.YYYY_MM_DD));
			quotation.setMaxPrice(data.getMaxPrice());
			quotation.setMinPrice(data.getMinPrice());
			quotation.setOpenPrice(data.getOpenPrice());
			quotation.setClosePrice(data.getClosePrice());
			quotation.setVolume(data.getVolume());
			quotation.setTransactionNumber(data.getTransactionNumber());
			quotationList.add(quotation);
		}
		quotationService.saveOrUpdate(quotationList);
		moveMouse();
	}
	
	private void activateCompanies(){
		List<GpwCompany> allComapanies=gpwCompanyService.findAll();
		sortCompanies(allComapanies);
		List<GpwCompany> findedCompanyList=new ArrayList<GpwCompany>();
		redirectToLoadCompanyData();
		List<WebElement> elements=driver.findElements(By.className("chart"));
		int companiesNumber=elements.size();
		int companyIndex=0;
		String xpath;
		String data;
		WebElement element;
		GpwCompany company;
		//TODO zmienic na i=2
		for(int i=2;i<companiesNumber*2;i++){
			if(companyIndex>=companiesNumber)
				break;
			try{
				xpath=comapnyDataXPath+i+"]/td[3]/a";
				element=driver.findElement(By.xpath(xpath));
				data=element.getText();
				data=data.trim();
				
				company=findCompany(allComapanies, data);//szukam pobranej spolki wsrod spolek z bazy danych
				if(company!=null){
					System.out.println("found "+company.getName());
					if(company.getShortName()==null){
						loadData(company, i); //uzupelniam dane, jesli nie zostaly jeszcze uzupelnione
					}
					
					findedCompanyList.add(company);
					allComapanies.remove(company);
					
					companyIndex++;
				}

			}catch(Exception e){
				continue;
			}
		}
		System.out.println(allComapanies.size());
		System.out.println(findedCompanyList.size());
		
		//dezaktywuje spolki nie znalezione w obecnej chwili na stronie GPW
		for(GpwCompany temp: allComapanies){
			if(temp.isActive()){
				temp.setActive(false);
				gpwCompanyService.saveOrUpdate(temp);
			}
		}
		
		//aktywuje te ktore sa obecnie na Gpw a byly nieaktywne
		for(GpwCompany temp: findedCompanyList){
			if(!temp.isActive()){
				if(JOptionPane.showConfirmDialog(null, "Czy spolke "+temp.getName()+" aktywowaæ?")==JOptionPane.YES_OPTION){
					temp.setActive(true);
					gpwCompanyService.saveOrUpdate(temp);
				}
			}
		}
	}
	
	/** sortowanie listy wg nazwy w kolejnosci rosnacej */
	private void sortCompanies(List<GpwCompany> list){
		boolean sorted=false;
		
		while(!sorted){
			sorted=true;
			for(int i=1;i<list.size();i++){
				if(list.get(i-1).getName().compareTo(list.get(i).getName())>=0){
					GpwCompany temp=list.get(i-1);
					list.remove(i-1);
					list.add(i, temp);
					sorted=false;
				}
			}
		}

	}
	
	/** wyszukuje spolki wsrod listy */
	private GpwCompany findCompany(List<GpwCompany> list, String name){
		for(GpwCompany company:list){
			if(company.getName().equals(name)){
				return company;
			}
		}
		return null;
	}
	
	/** pobiera dane spolki do uzupelnienia ze strony i zapisuje zmiany na bazie danych */
	private void loadData(GpwCompany company, int indexToLoad){
		System.out.println("loading process... "+company.getName());
		String xpath;
		WebElement element;
		
		xpath=comapnyDataXPath+indexToLoad+"]/td[4]";
		element=driver.findElement(By.xpath(xpath));
		company.setShortName(element.getText().trim());
		
		xpath=comapnyDataXPath+indexToLoad+"]/td[5]";
		element=driver.findElement(By.xpath(xpath));
		
		Currency currency=currencyService.findByName(element.getText().trim());
		if(currency==null){
			currency=createCurrency(element.getText().trim());
		}
		company.setCurrency(currency);
		
		gpwCompanyService.saveOrUpdate(company);
	}
	
	
//	/** przekierowanie na strone z danymi oraz pobranie potrzebnych danych i zapisanie do bazy danych */
//	private void loadCompanyData(){
//		System.out.println("Ladowanie danych spolki");
//		redirectToLoadCompanyData();
//		WebElement element;
//		String xpath;
//		String data;
//		if(indexToLoad==null)
//			indexToLoad=2;
//	int last=-1;
//		while(true){
//			indexToLoad++;
//			try{
//				xpath=comapnyDataXPath+indexToLoad+"]/td[3]/a";
//				element=driver.findElement(By.xpath(xpath));
//				data=element.getText();
//				data=data.trim();
//				GpwCompany company;
//				for(int x=0;x<companyListToComplete.size();x++){
//					company=companyListToComplete.get(x);
//					if(company.getName().equals(data)){
//						xpath=comapnyDataXPath+indexToLoad+"]/td[4]";
//						element=driver.findElement(By.xpath(xpath));
//						company.setShortName(element.getText().trim());
//						
//						xpath=comapnyDataXPath+indexToLoad+"]/td[5]";
//						element=driver.findElement(By.xpath(xpath));
//						Currency currency=currencyService.findByName(element.getText().trim());
//						if(currency==null){
//							currency=createCurrency(element.getText().trim());
//						}
//						company.setCurrency(currency);
//						gpwCompanyService.saveOrUpdate(company);
//						companyListToComplete.remove(x);
//						x--;
//					}
//				}
//				if(companyListToComplete.size()==0)
//					break;
//				if(indexToLoad>500){
//					indexToLoad=2;
//					companyListToComplete=new ArrayList<GpwCompany>();
//					break;
//				}
//			}catch(Exception e){
//				if(last<0)  
//					last=indexToLoad;
//				if(indexToLoad-last>4){
//					System.out.println("koniec "+indexToLoad);
//					indexToLoad=2;
//					companyListToComplete=new ArrayList<GpwCompany>();
//					break;
//				}
//			}
//		}
//	}
	
	
	private void moveMouse(){
		new Thread(){
			public void run(){
				try {
					Robot robot=new Robot();
					Random random=new Random();
					int x=random.nextInt(1000);
					int y=random.nextInt(1000);
					robot.mouseMove(x, y);
				} catch (AWTException e) {
//					throw new RuntimeException(e);
				}
			}
		}.start();
		
		
	}
	
	private Currency createCurrency(String currencyName){
		Currency currency=new Currency();
		currency.setName(currencyName);
		currencyService.saveOrUpdate(currency);
		return currency;
	}
	
	private boolean checkIsWorkDay(){
		try{
			WebElement element=driver.findElement(By.className("colFL"));
			if(element!=null){
				return true;
			}
			else{
				return false;			
			}
		}catch(Exception e){
			return false;
		}

	}
	private List<ImportData> loadData(){
		int rowId=2;
		ImportData data;
		List<ImportData> datalist=new ArrayList<GpwParserManager.ImportData>();
		StringBuffer buffer;
		String row;
		while(true){
			data=new ImportData();
			try{
				row=prepareRowString(rowId++);
				buffer=new StringBuffer(row);
				buffer.append("]/td[1]");
				data.name=driver.findElement(By.xpath(buffer.toString())).getText();
				System.out.println(data.name);
	
				buffer=new StringBuffer(row);
				buffer.append("]/td[2]");
				data.isin=driver.findElement(By.xpath(buffer.toString())).getText();
				
				buffer=new StringBuffer(row);
				buffer.append("]/td[4]");
				data.openPrice=driver.findElement(By.xpath(buffer.toString())).getText();
				
				buffer=new StringBuffer(row);
				buffer.append("]/td[5]");
				data.maxPrice=driver.findElement(By.xpath(buffer.toString())).getText();
				
				buffer=new StringBuffer(row);
				buffer.append("]/td[6]");
				data.minPrice=driver.findElement(By.xpath(buffer.toString())).getText();
				
				buffer=new StringBuffer(row);
				buffer.append("]/td[7]");
				data.closePrice=driver.findElement(By.xpath(buffer.toString())).getText();

				buffer=new StringBuffer(row);
				buffer.append("]/td[9]");
				data.volume=driver.findElement(By.xpath(buffer.toString())).getText();
				
				buffer=new StringBuffer(row);
				buffer.append("]/td[10]");
				data.transactionNumber=driver.findElement(By.xpath(buffer.toString())).getText();
				
			}catch(Exception e){
				break;
			}
			
			datalist.add(data);
		}
		
		return datalist;
	}
	
	private String prepareRowString(int i){
		StringBuffer buffer=new StringBuffer();
		buffer.append(quotationXPath1);
		buffer.append(i);
		return buffer.toString();
	}
	
	private void printReport(String text) {
		GregorianCalendar date = new GregorianCalendar();
		String fileName = DateUtils.convertToStringFormat(date,
				DateFormat.YYYY_MM_DD);
		try {
			File file=new File("configs/reports/gpw/import_reports");
			file.mkdirs();
			FileWriter writer = new FileWriter("configs/reports/gpw/import_reports/" + fileName
					+ ".log", true);
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void close() {
		driver.close();
	}
	
	private class ImportData{
		String name;
		String shortName;
		String isin;
		String currency;
		String openPrice;
		String closePrice;
		String maxPrice;
		String minPrice;
		String transactionNumber;
		String volume;
		
		private String transform(String stringPrice){
			String converted=stringPrice.replace(',', '.');
			return converted;
		}
		
		BigDecimal getOpenPrice(){
			return new BigDecimal(transform(openPrice).replaceAll("\\s+", ""));
		}
		BigDecimal getClosePrice(){
			return new BigDecimal(transform(closePrice).replaceAll("\\s+", ""));
		}
		BigDecimal getMaxPrice(){
			return new BigDecimal(transform(maxPrice).replaceAll("\\s+", ""));
		}
		BigDecimal getMinPrice(){
			return new BigDecimal(transform(minPrice).replaceAll("\\s+", ""));
		}
		BigDecimal getVolume(){
			return new BigDecimal(transform(volume.replaceAll("\\s+", "")));
		}
		Integer getTransactionNumber(){
			return new Integer(transactionNumber.replaceAll("\\s+", ""));
		}
		
	}
}
