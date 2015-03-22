package com.victor.skandia.control;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import algorithm.TwoAverages;

import com.victor.entity.FundValue;
import com.victor.entity.InvestmentFund;
import com.victor.entity.SignalWeight;
import com.victor.entity.SkandiaAlgorithm;
import com.victor.service.FundValueService;
import com.victor.service.InvestmentFundService;
import com.victor.service.PortfolioFundService;
import com.victor.service.SkandiaAlgorithmService;
import com.victor.skandia.model.SkandiaSimulationResult;
import com.victor.skandia.model.TestResult;
import com.victor.utils.Algorithm;
import com.victor.utils.DateFormat;
import com.victor.utils.DateUtils;

/**
 * @author Wiktor
 *
 */
@Controller
public class SkandiaAlgorithManager {
	@Autowired
	FundValueService fundValueService;
	@Autowired
	SkandiaAlgorithmService skandiaAlgorithmService;
	@Autowired
	InvestmentFundService investmentFundService;
	@Autowired
	PortfolioFundService portfolioFundService;

	
	List<InvestmentFund> fundList;
	Set<SkandiaAlgorithm> algorithmVersionList;
	Map<SkandiaAlgorithm, List<FundValue>> openTransactionSignalMap;
	Map<SkandiaAlgorithm,List<FundValue>> closeTransactionSignalMap;
	
	/** liczba lat dla jak dlugiego okresu beda prowadzone testy */
	private static int yearAmount;
	/** liczba algorytmow kazdego rodzaju ktore beda trestowane */
	private static int algorithmAmount;
	/** kapital poczatkowy dla testow algorytmow */
	private static BigDecimal startCapital;
	/** opoznienie pomiedzy wygenerowaniem sygnalu a realizacja transakcji (tu konwersji portfela) */
	private static int delay;
	private GregorianCalendar startDate;
	private GregorianCalendar endDate;

	
	/**
	 * {@link SkandiaAlgorithManager}
	 */
	public SkandiaAlgorithManager() {
		loadConfig();
		startDate=new GregorianCalendar();
		endDate=new GregorianCalendar();
		startDate.add(GregorianCalendar.YEAR, -yearAmount);
	}
	
	public static void main(String ...a){
		new SkandiaAlgorithManager();
	}
	
	private void loadConfig(){
		try {
			@SuppressWarnings("resource")
			Scanner reader=new Scanner(new File("configs/skandia/algorithm.config"));
			String line=reader.nextLine();
			line=line.substring(line.indexOf(":")+1,line.length());
			startCapital=new BigDecimal(line);
			line=reader.nextLine();
			line=line.substring(line.indexOf(":")+1,line.length());
			algorithmAmount=new Integer(line);
			line=reader.nextLine();
			line=line.substring(line.indexOf(":")+1,line.length());
			yearAmount=new Integer(line);
			line=reader.nextLine();
			line=line.substring(line.indexOf(":")+1,line.length());
			delay=new Integer(line);
		} catch (FileNotFoundException e) {
			startCapital=new BigDecimal(24000.00);
			algorithmAmount=1000;
			yearAmount=5;
			delay=4;
		}
	}
	public List<InvestmentFund> getActiveFundList(){
		return investmentFundService.findAllActive();
	}
	
	/** wykonuje testy i ustawia najlepsze algorytmy dla kazdego funduszu indywidualnie */
	public TestResult makeAlgorithmTest(InvestmentFund fund){
		return doTest(fund);
//		test(fundList.get(0));//TODO do wymiany
	}
	
	
	/** utrwala wybor algorytmow przez uzytkownika */
	public void changeChoosenAlgorithm(SkandiaSimulationResult simulationResult, InvestmentFund fund){
		desactivateAlgorithm(fund);
		activateAlgoritms(simulationResult, fund);
	}
	
	/** dezaktywuje wybrane algorytmy dla funduszu */
	public void desactivateAlgorithm(InvestmentFund fund){
		SkandiaAlgorithm algorithm=skandiaAlgorithmService.findActiveOpenAlgorithm(fund);
		if(algorithm!=null){
			algorithm.setActive(false);
			algorithm.setDesactivateDate(new GregorianCalendar());
			skandiaAlgorithmService.saveOrUpdate(algorithm);
		}
		
		algorithm=skandiaAlgorithmService.findActiveCloseAlgorithm(fund);
		if(algorithm!=null){
			algorithm.setActive(false);
			algorithm.setDesactivateDate(new GregorianCalendar());
			skandiaAlgorithmService.saveOrUpdate(algorithm);
		}
	}
	
	/** aktywuje algorytmu dla danego funduszu ustawia relacje, wylicza potrzebne pola i zapisuje do bazy danych */
	private void activateAlgoritms(SkandiaSimulationResult simulationResult, InvestmentFund fund){
		SkandiaAlgorithm openingAlgorithm=new SkandiaAlgorithm(simulationResult.getOpenAlgorithm());
		openingAlgorithm.loadDataFromAlgorithm();
		openingAlgorithm.setInvestmentFund(fund);
		openingAlgorithm.setActive(true);
		openingAlgorithm.setOpeningAlgorithm(true);
		openingAlgorithm.setCreateDate(new GregorianCalendar());
		skandiaAlgorithmService.saveOrUpdate(openingAlgorithm);
		fund.setOpeningAlgorithm(openingAlgorithm);
		
		SkandiaAlgorithm closingAlgorithm=new SkandiaAlgorithm(simulationResult.getCloseAlgorithm());
		closingAlgorithm.loadDataFromAlgorithm();
		closingAlgorithm.setInvestmentFund(fund);
		closingAlgorithm.setActive(true);
		closingAlgorithm.setOpeningAlgorithm(false);
		closingAlgorithm.setCreateDate(new GregorianCalendar());
		skandiaAlgorithmService.saveOrUpdate(closingAlgorithm);
		fund.setClosingAlgorithm(closingAlgorithm);
		
		SignalWeight weight=fund.getWeight();
		weight.setEfficiency(simulationResult.getEfficiencyInPercentage());
		weight.setRateOfProfit(simulationResult.getRateOfProfit(startCapital));		
		investmentFundService.saveOrUpdate(fund);
	}
	
	/** wybiera najlepszy algorytm poprzez losowanie puli algorytmow ktore beda badane */
	private TestResult doTest(InvestmentFund fund){
		algorithmVersionList=new HashSet<SkandiaAlgorithm>();
		openTransactionSignalMap=new HashMap<SkandiaAlgorithm, List<FundValue>>();
		closeTransactionSignalMap=new HashMap<SkandiaAlgorithm, List<FundValue>>();
		prepareToTest(fund);
		moveMouse();
		System.out.println("prepare signal...");
		prepareTransactionSignalMap(fund);
		List<SkandiaSimulationResult> resultList=chooseBestAlgorithm(fund.getShortName());
		sortSimulationResult(resultList);
		SkandiaSimulationResult result=resultList.get(0); // najlepszy wynik jest zawsze ostatni
		System.out.println("move mouse2");
		moveMouse();
		TestResult testResult=new TestResult();
		testResult.setChoosenResult(result);
		resultList.remove(result);
		testResult.setRestResltList(resultList);
		testResult.setInvestmentFund(fund);
		testResult.setBeningCapital(startCapital);
		desactivateAlgorithm(fund);
		activateAlgoritms(result, fund);
		clearReports();
		return testResult;
	}
	
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
	
	@SuppressWarnings("unused")
	private void test(InvestmentFund fund){
		SkandiaAlgorithm openAlgorithm = new SkandiaAlgorithm(new TwoAverages(8,16));
		SkandiaAlgorithm closeAlgorithm=new SkandiaAlgorithm(new TwoAverages(3,5));
		
		BigDecimal shareAmount = BigDecimal.ZERO;
		BigDecimal shareValue;
		BigDecimal capital=startCapital;
		SkandiaSimulationResult result=new SkandiaSimulationResult();
		
		List<FundValue> priceList=fundValueService.findByInvestmentFundWithDateLimit(fund, startDate, endDate);
		openAlgorithm.loadAlgorithmToTest(priceList);
		closeAlgorithm.loadAlgorithmToTest(priceList);
		boolean transaction=false;
		
		for(int i=0;i<priceList.size();i++){
			openAlgorithm.putPrice(priceList.get(i));
			closeAlgorithm.putPrice(priceList.get(i));
			
			if(openAlgorithm.openTransaction() && !closeAlgorithm.closeTransaction()&& !transaction){
				if(i+delay<priceList.size()){
					transaction=true;
					shareAmount=calculateShareAmount(capital, priceList.get(i+delay).getValue());
					System.out.println(">"+priceList.get(i+delay).getValue());
				}
			}
			if(closeAlgorithm.closeTransaction()&& transaction){
				if(i+delay<priceList.size()){
					transaction=false;
					shareValue=caluculateShareValue(shareAmount, priceList.get(i+delay).getValue());
					System.out.println("< "+priceList.get(i+delay).getValue());
					if(shareValue.compareTo(capital)>0)
						result.positiveTransaction();
					 else
						 result.negativeTransaction();
					capital=shareValue;
				}
			}
		}
		System.out.println(result.getPositiveTransactionAmount()+"\t"+result.getNegaticeTransactionAmount()+"\t"+capital);
		
		
	}

	
	/** przygotowuje pule algoprytmow ktore beda testowane */
	private void prepareToTest(InvestmentFund fund){
		Set<Class<?extends Algorithm>>algorithmClassSet=loadAlgorithmClassList();
		// zaladowanie algorytmow ktore do tej pory wygraly
		List<SkandiaAlgorithm> skandiaAlgorithmList=skandiaAlgorithmService.findAllByFund(fund);
		for(SkandiaAlgorithm algorithm: skandiaAlgorithmList){
			algorithm.loadAlgorithm();
			algorithmVersionList.add(algorithm);
		}
		//przygotowanie losowych algorytmow wczytanych refleksyjnie klas
		SkandiaAlgorithm temp;
		for( Class<? extends Algorithm> algorithmClass: algorithmClassSet){
			temp=new SkandiaAlgorithm(algorithmClass, fund);
			algorithmVersionList.addAll(temp.getRandomAlgorithmVersion(algorithmAmount));
		}
//		algorithmVersionList.addAll(skandiaAlgorithmList);
	}

	
	/** przygotowuje mapy sygnalow kupna i sprzedazy dla wszystkich wylosowanych algorytmow  */
	private void prepareTransactionSignalMap(InvestmentFund fund){
		List<FundValue> priceList=fundValueService.findByInvestmentFundWithDateLimit(fund, startDate, endDate);
		List<FundValue> openTransactionSignalList;
		List<FundValue> closeTransactionSignalList;
		//przygotowuje listy sygnalow otwrcia lub zamkniecia transakcji mapowane po algorytmach
		for(SkandiaAlgorithm algorithm: algorithmVersionList){
			openTransactionSignalList=new ArrayList<FundValue>();
			closeTransactionSignalList=new ArrayList<FundValue>();
			prepareTransactionSignals(algorithm, priceList, openTransactionSignalList, closeTransactionSignalList, fund.getShortName());
			openTransactionSignalMap.put(algorithm, openTransactionSignalList);
			closeTransactionSignalMap.put(algorithm, closeTransactionSignalList);
		}
		
	}
	
	/** zaczytuje refleksyjnie klasy algorytmow */
	@SuppressWarnings("unchecked")
	private Set<Class<?extends Algorithm>> loadAlgorithmClassList(){
		Set<Class<? extends Algorithm>> algorithmClassList=new HashSet<Class<? extends Algorithm>>();
		File path=new File("algorithm");
		File[] algorytmList=path.listFiles();
		StringBuffer sb;
		for(File file: algorytmList)
		{
			if(file.getName().indexOf(".class")>0)
			{
				//przygotowanie String do wczytania klasy
				sb=new StringBuffer();
				sb.append("algorithm").append(".");
				sb.append(file.getName().substring(0, file.getName().indexOf(".class")));			
				
				Class<? extends Algorithm> algorithmClass;
				try {
					algorithmClass = (Class<? extends Algorithm>) Class.forName(sb.toString());
					algorithmClassList.add(algorithmClass);
				} catch (ClassNotFoundException e) {
					
				}
				
			}
		}
		return algorithmClassList;
	}
	
	/** przygotowuje listy sygnalow kupna i sprzedazy */
	private void prepareTransactionSignals(SkandiaAlgorithm algorithm, List<FundValue> priceList, List<FundValue> openTransactionSignalList, List<FundValue> closeTransactionSignalList, String fundName){
		try {
			//sprawdzam czy nie zostaly juz zapisane sygnaly dla danego algorytmu, jezeli byly to zostana zaczytane i przekazane poprzez argumenty metody
			if(loadReport(fundName, algorithm.toString(), openTransactionSignalList, closeTransactionSignalList))
				return;
		} catch (IOException e) {
			System.out.println("nieudane ladowanie raportu");
		}
		if(!algorithm.loadAlgorithmToTest(priceList))
			return;
		
		int maxLength=algorithm.getLengthListToLoad();
		for(int i=maxLength;i<priceList.size();i++){
			algorithm.putPrice(priceList.get(i));
			if(algorithm.openTransaction()){
				if(i+delay<priceList.size()){
					openTransactionSignalList.add(priceList.get(i+delay));
				}
			} else if(!algorithm.openTransaction()){
			}
			
			if(algorithm.closeTransaction()){ 
				if(i+delay<priceList.size()){
					closeTransactionSignalList.add(priceList.get(i+delay));
				}
			} else if(!algorithm.closeTransaction()){
			}
		}
		printReport(fundName, algorithm.toString(), openTransactionSignalList, closeTransactionSignalList);
	}
	
	/** wybiera najlepsza pare algrytmow na podstawie map sygnalow kupna i sprzedazy */
	private List<SkandiaSimulationResult> chooseBestAlgorithm(String fundName){
		List<SkandiaSimulationResult> resultList=new ArrayList<SkandiaSimulationResult>();
		SkandiaSimulationResult bestResult = null;
		SkandiaSimulationResult result;
		//dla wszystkich algorytmow jako algorytmy otwierajace
		for(SkandiaAlgorithm openAlgorithm: algorithmVersionList){
			//dla wszystkich algorytmow jako algorytmy zamykajace
			for(SkandiaAlgorithm closeAlgorithm: algorithmVersionList){
				result=countRateOfReturn(openAlgorithm, closeAlgorithm);
				if(result!=null){
					if(bestResult==null){
						if(result.getCapital().compareTo(BigDecimal.ZERO)>0){
							resultList.add(result);
							bestResult=result;
							printReport(result, fundName);
						}
					} else if(bestResult.getCapital().compareTo(result.getCapital())<0 && result.getPositiveTransactionAmount()>3){
						resultList.add(result);
						bestResult=result;
						printReport(result, fundName);
					}
				}
			}
		}
		
		return resultList;
	}
	
	/** oblicza kapital koncowy dla pary algorytmow.<br/>
	 * <b>zasady:</b><br/> 
	 * sygnal otwarcia gdy algorytm otwarcia generuje sugnal i algorytm zamkniecia nie generuje sygnalu<br/>
	 * sygnal zamkniecia gdy algorytm zamkniecia generuje sygnal
	 *  */
	@SuppressWarnings("static-access")
	private SkandiaSimulationResult countRateOfReturn(SkandiaAlgorithm openAlgorithm, SkandiaAlgorithm closeAlgorithm){
		System.out.println("*******************************************************");
		System.out.println(openAlgorithm+"\t"+closeAlgorithm);
		System.out.println("*******************************************************");
		
		SkandiaSimulationResult result=new SkandiaSimulationResult();
		result.setOpenAlgorithm(openAlgorithm);
		result.setCloseAlgorithm(closeAlgorithm);
		BigDecimal capital=new BigDecimal(this.startCapital.doubleValue());
		List<FundValue> openTransactionSignalList=openTransactionSignalMap.get(openAlgorithm);
		List<FundValue> closeTransactionSignalList=closeTransactionSignalMap.get(closeAlgorithm);
		//gdy ktoras z list syganlow jest pusta, przyjmowane sa warunki niespelniajace wygranej 
		//proces symulacji zostaje przerwany, bo zadna z transakcji nie zostanie zakonczona w pelni
		if(openTransactionSignalList.size()==0 || closeTransactionSignalList.size()==0){
			result.setCapital(BigDecimal.ZERO);
			return result;
		}
		//liczba zakupionych jednostek funduszu
		BigDecimal shareAmount;
		//wartosc sprzedanych jednostek funduszu
		BigDecimal shareValue;
		int closeIndex=0;
		transactionProcess:
		for(int openIndex=0;openIndex<openTransactionSignalList.size();openIndex++){
			//szukam sygnalu zamkniecia ktory nie jest wczesniej niz sygnal otwarcia
			//closeSignal.date>=openSigaml.date
			while(closeTransactionSignalList.get(closeIndex).getDate().compareTo(openTransactionSignalList.get(openIndex).getDate())<0){
				closeIndex++;
				if(!(closeIndex<closeTransactionSignalList.size())){
					break transactionProcess;
				}
			}
			//gdy tego samego dnia wygenerowany jest syganl otwarcia i zamkniecia transakcja nie moze zostac otwarta
			if(closeTransactionSignalList.get(closeIndex).getDate().compareTo(openTransactionSignalList.get(openIndex).getDate())==0){
				continue;
			}
			//gdy daty sa rozne (sygnal zamkniecia jest pozniej niz sygnal otwarcia
			//obliczana transakcja dla kursu otwarcia z listy sygnalow otwierajacych i dla kursu zamkniecia z listy sygnalow zamykajacych
			else{
				if((openIndex)<openTransactionSignalList.size() && (closeIndex)<closeTransactionSignalList.size()){
					shareAmount=calculateShareAmount(capital, openTransactionSignalList.get(openIndex).getValue());
					shareValue=caluculateShareValue(shareAmount, closeTransactionSignalList.get(closeIndex).getValue());
					if(capital.compareTo(shareValue)<0){
						result.positiveTransaction();
					} else {
						result.negativeTransaction();
					}
					capital=shareValue;
					
					//szukam sygnalu otwarcia ktory nie jest wczesniej niz ostatnio zaobserwowany
					//openSignal.date>=closeSignal.date
					while(openTransactionSignalList.get(openIndex).getDate().compareTo(closeTransactionSignalList.get(closeIndex).getDate())<0){
						openIndex++;
						if(!(openIndex<openTransactionSignalList.size()))
							break transactionProcess;
					}
				}else{
					break transactionProcess;
				}
			}
		}
		result.setCapital(capital);
		return result;
	}
	
	/** oblicza liczbe jednostek funduszu jaka mozna kupic za okreslona cene. Jednostki funduszy mozna kupowaæ w ilosciach ulamkowych 
	 * @param capital wartosc za ktora beda kupowane jednostki funduszu
	 * @param price cena jednostki funduszu */
	public BigDecimal calculateShareAmount(BigDecimal capital, BigDecimal price){
		if(capital.compareTo(BigDecimal.ZERO)>0 && price.compareTo(BigDecimal.ZERO)>0){
			BigDecimal shareAmount=capital.divide(price,new MathContext(3));
			return shareAmount;
		}
		else return BigDecimal.ZERO;
	}
	
	/** oblicza wartosc jednostek funduszu na podstawie ceny
	 * @param amount ilosc jednostek
	 * @param price cena jednostki funduszu */
	private BigDecimal caluculateShareValue(BigDecimal amount, BigDecimal price){
		BigDecimal shareValue=price.multiply(amount);
		return shareValue;
	}
	
	

	
	/** sortuje wyniki symulacji testu wedlug skutecznosci od najwyzszej do najnizszej*/
	private void sortSimulationResult(List<SkandiaSimulationResult> resultList){
		boolean sorted=false;
		
		while(!sorted){
			sorted=true;
			for(int i=1;i<resultList.size();i++){
				if(resultList.get(i-1).getCapital().compareTo(resultList.get(i).getCapital())<0){
					SkandiaSimulationResult temp=resultList.get(i);
					resultList.set(i, resultList.get(i-1));
					resultList.set(i-1, temp);
					i--;
					sorted=false;
				}
			}
		}
		sorted=false;
		while(!sorted){
			sorted=true;
			for(int i=1;i<resultList.size();i++){
				if(resultList.get(i-1).getEfficiencyInPercentage().compareTo(resultList.get(i).getEfficiencyInPercentage())<0){
					SkandiaSimulationResult temp=resultList.get(i);
					resultList.set(i, resultList.get(i-1));
					resultList.set(i-1, temp);
					i--;
					sorted=false;
				}
			}
		}
	}

	
	//***********************************************************************
	//**************************   RAPORTY   ********************************
	//***********************************************************************
	
	/** zapisuje do pliku raporty dla sygnalow otwarcia i zamkniecia pozycji dla danego funduszu w formacie xls */
	private void printReport(String fundName, String algorithmName, List<FundValue> openTransactionSignalList, List<FundValue> closeTransactionSignalList) {
		
		if(fundName==null || algorithmName==null || openTransactionSignalList==null || closeTransactionSignalList==null)
			return;
		StringBuffer buffer;
		File file=new File("configs/reports/skandia/open_transaction_signal");
		file.mkdirs();
		
		if(openTransactionSignalList.size()>0){
			buffer=new StringBuffer();
			buffer.append("configs/reports/skandia/open_transaction_signal/");
			buffer.append(fundName).append("_").append(algorithmName).append(".xls");
			try {
				FileWriter writer = new FileWriter(buffer.toString());
				for(FundValue fund:openTransactionSignalList){
					buffer=new StringBuffer();
					buffer.append(fund.getId()).append(";");
					buffer.append(DateUtils.convertToStringFormat(fund.getDate(), DateFormat.YYYY_MM_DD)).append(";");
					buffer.append(fund.getValue()).append("\n");
					writer.write(buffer.toString());
				}
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		file=new File("configs/reports/skandia/close_transaction_signal");
		file.mkdirs();
		if(closeTransactionSignalList.size()>0){
			buffer=new StringBuffer();
			buffer.append("configs/reports/skandia/close_transaction_signal/");
			buffer.append(fundName).append("_").append(algorithmName).append(".xls");
			try {
				FileWriter writer = new FileWriter(buffer.toString());
				for(FundValue fund:closeTransactionSignalList){
					buffer=new StringBuffer();
					buffer.append(fund.getId()).append(";");
					buffer.append(DateUtils.convertToStringFormat(fund.getDate(), DateFormat.YYYY_MM_DD)).append(";");
					buffer.append(fund.getValue()).append("\n");
					writer.write(buffer.toString());
				}
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private void clearReports(){
		File file =new File("configs/reports/skandia/open_transaction_signal");
		File[] fileTable=file.listFiles();
		for(File report:fileTable){
			report.delete();
		}
		
		file =new File("configs/reports/skandia/close_transaction_signal");
		fileTable=file.listFiles();
		for(File report:fileTable){
			report.delete();
		}
	}
	
	/** zapisuje do pliku raport wyniku symulacji algorytmu (testu) */
	private void printReport(SkandiaSimulationResult result, String fundName) {
		
		File file=new File("configs/reports/skandia/test_result");
		file.mkdirs();
		StringBuffer buffer=new StringBuffer();
		buffer.append("configs/reports/skandia/test_result/");
		buffer.append(fundName).append(".xls");
		int sum=result.getPositiveTransactionAmount()+result.getNegaticeTransactionAmount();
		BigDecimal efficiency=new BigDecimal(sum);
		efficiency=new BigDecimal(result.getPositiveTransactionAmount()).divide(efficiency, MathContext.DECIMAL64);
		try {
			FileWriter writer = new FileWriter(buffer.toString(), true);
			buffer=new StringBuffer();
			buffer.append(result.getOpenAlgorithm().toString()).append(";");
			buffer.append(result.getCloseAlgorithm().toString()).append(";");
			buffer.append(result.getPositiveTransactionAmount()).append(";");
			buffer.append(result.getNegaticeTransactionAmount()).append(";");
			buffer.append(efficiency).append(";");
			buffer.append(result.getCapital()).append("\n");
			writer.write(buffer.toString());
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/** sprawdza i pobiera raport sygnalow otwarcia i zamkniecia<br/>
	 * @return true gdy znalazl zapisany raport, false gdy nie znalazl raportu 
	 * @throws IOException */
	@SuppressWarnings("resource")
	private boolean loadReport(String fundName, String algorithmName,List<FundValue> openTransactionSignalList, List<FundValue> closeTransactionSignalList) throws IOException{
		if(fundName==null || algorithmName==null)
			return false;
		
		StringBuffer buffer=new StringBuffer();
		buffer.append("configs/reports/skandia/open_transaction_signal/");
		buffer.append(fundName).append("_").append(algorithmName).append(".xls");
		File file=new File(buffer.toString());
		if(file.createNewFile()){
			file.delete();
			return false;
		}
		
		Scanner writer=new Scanner(file);
		StringTokenizer token;
		GregorianCalendar date;
		FundValue value;
		while(writer.hasNextLine()){
			token=new StringTokenizer(writer.nextLine(),";", false);
			value=new FundValue();
			value.setId(new Long(token.nextToken()));
			value.setDate(DateUtils.convertToDate(token.nextToken()));
			value.setValue(new BigDecimal(token.nextToken()));
			openTransactionSignalList.add(value);
		}
		
		buffer=new StringBuffer();
		buffer.append("configs/reports/skandia/close_transaction_signal/");
		buffer.append(fundName).append("_").append(algorithmName).append(".xls");
		file=new File(buffer.toString());
		if(file.createNewFile())
			return false;
		
		writer=new Scanner(file);

		while(writer.hasNextLine()){
			token=new StringTokenizer(writer.nextLine(),";", false);
			value=new FundValue();
			value.setId(new Long(token.nextToken()));
			value.setDate(DateUtils.convertToDate(token.nextToken()));
			value.setValue(new BigDecimal(token.nextToken()));
			closeTransactionSignalList.add(value);
		}
		return true;
	}
	
	private void printReport(SkandiaAlgorithm algorithm){
		try {
			File file=new File("configs/reports/skandia/test_result");
			file.mkdirs();
			
			FileWriter writer = new FileWriter("configs/reports/skandia/test_result/won.xls", true);
			StringBuffer buffer=new StringBuffer();
			buffer.append(algorithm.getInvestmentFund().getShortName()).append(";");
			buffer.append(algorithm.getId()).append(";");
			buffer.append(algorithm.getName()).append(";");
			buffer.append(algorithm.getParameter()).append(";");
			buffer.append(algorithm.isOpeningAlgorithm()).append(";");
			if(algorithm.isActive())
				buffer.append("activated\n");
			else
				buffer.append("disactivated\n");
			writer.write(buffer.toString());
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static int getDellay(){
		return delay;
	}
}
