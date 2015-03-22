package com.victor.gpw.control;

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

import com.victor.entity.GpwAlgorithm;
import com.victor.entity.GpwCompany;
import com.victor.entity.Quotation;
import com.victor.entity.SignalWeight;
import com.victor.gpw.model.GpwSimulationResult;
import com.victor.service.GpwAlgorithmService;
import com.victor.service.GpwCompanyService;
import com.victor.service.QuotationService;
import com.victor.skandia.model.TestResult;
import com.victor.utils.Algorithm;
import com.victor.utils.DateFormat;
import com.victor.utils.DateUtils;

/**
 * @author Wiktor Rosinski<br/>
 *
 */
@Controller
public class GpwAlgorithmManager {

	@Autowired
	private QuotationService quotationService;
	@Autowired
	private GpwAlgorithmService gpwAlgorithmService;
	@Autowired
	private GpwCompanyService gpwCompanyService;

	/** liczba lat dla jak dlugiego okresu beda prowadzone testy */
	private static int yearAmount;
	/** liczba algorytmow kazdego rodzaju ktore beda trestowane */
	private static int algorithmAmount;
	/** kapital poczatkowy dla testow algorytmow */
	private static BigDecimal startCapital;
	private GregorianCalendar startDate;
	private GregorianCalendar endDate;

	private Set<GpwAlgorithm> algorithmVersionList;
	Map<GpwAlgorithm, List<Quotation>> openTransactionSignalMap;
	Map<GpwAlgorithm, List<Quotation>> closeTransactionSignalMap;

	/** {@link GpwAlgorithmManager} */
	public GpwAlgorithmManager() {
		loadConfig();
		startDate = new GregorianCalendar();
		endDate = new GregorianCalendar();
		startDate.add(GregorianCalendar.YEAR, -yearAmount);
	}

	/**
	 * wykonuje testy i ustawia najlepsze algorytmy dla kazdej spolki
	 * indywidualnie
	 */
	public TestResult makeAlgorithmTest(GpwCompany company) {
		return doTest(company);
		// test(fundList.get(0));//TODO do wymiany
	}

	private void loadConfig() {
		try {
			@SuppressWarnings("resource")
			Scanner reader = new Scanner(new File(
					"configs/gpw/algorithm.config"));
			String line = reader.nextLine();
			line = line.substring(line.indexOf(":") + 1, line.length());
			startCapital = new BigDecimal(line);
			line = reader.nextLine();
			line = line.substring(line.indexOf(":") + 1, line.length());
			algorithmAmount = new Integer(line);
			line = reader.nextLine();
			line = line.substring(line.indexOf(":") + 1, line.length());
			yearAmount = new Integer(line);
		} catch (FileNotFoundException e) {
			startCapital = new BigDecimal(24000.00);
			algorithmAmount = 1000;
			yearAmount = 5;
		}
	}

	public List<GpwCompany> getActiveCompanies() {
		return gpwCompanyService.findAllActive();
	}

	/**
	 * wybiera najlepszy algorytm poprzez losowanie puli algorytmow ktore beda
	 * badane
	 */
	private TestResult doTest(GpwCompany company) {
		algorithmVersionList = new HashSet<GpwAlgorithm>();
		openTransactionSignalMap = new HashMap<GpwAlgorithm, List<Quotation>>();
		closeTransactionSignalMap = new HashMap<GpwAlgorithm, List<Quotation>>();
		System.out.println("prepare to test..");
		prepareToTest(company);

		prepareTransactionSignalMap(company);
		List<GpwSimulationResult> resultList = chooseBestAlgorithm(company
				.getName());
		sortSimulationResult(resultList);
		GpwSimulationResult result;
		if (resultList.size() > 0) {
			result = resultList.get(0); // najlepszy wynik jest zawsze ostatni
		} else {
			return null;
		}

		moveMouse();
		TestResult testResult = new TestResult();
		testResult.setChoosenResult(result);
		resultList.remove(result);
		testResult.setRestResltList(resultList);
		testResult.setCompany(company);
		testResult.setBeningCapital(startCapital);
		desactivateAlgorithm(company);
		activateAlgoritms(result, company);
		clearReports();
		return testResult;
	}

	/** przygotowuje pule algoprytmow ktore beda testowane */
	private void prepareToTest(GpwCompany company) {
		Set<Class<? extends Algorithm>> algorithmClassSet = loadAlgorithmClassList();
		// zaladowanie algorytmow ktore do tej pory wygraly
		List<GpwAlgorithm> gpwAlgorithmList = gpwAlgorithmService
				.findAllByCompany(company);
		for (GpwAlgorithm algorithm : gpwAlgorithmList) {
			algorithm.loadAlgorithm();
			algorithmVersionList.add(algorithm);
		}
		// przygotowanie losowych algorytmow wczytanych refleksyjnie klas
		GpwAlgorithm temp;
		for (Class<? extends Algorithm> algorithmClass : algorithmClassSet) {
			temp = new GpwAlgorithm(algorithmClass, company);
			algorithmVersionList.addAll(temp
					.getRandomAlgorithmVersion(algorithmAmount));
		}
		// algorithmVersionList.addAll(gpwAlgorithmList);
	}

	/** zaczytuje refleksyjnie klasy algorytmow */
	@SuppressWarnings("unchecked")
	private Set<Class<? extends Algorithm>> loadAlgorithmClassList() {
		Set<Class<? extends Algorithm>> algorithmClassList = new HashSet<Class<? extends Algorithm>>();
		File path = new File("algorithm");
		File[] algorytmList = path.listFiles();
		StringBuffer sb;
		for (File file : algorytmList) {
			if (file.getName().indexOf(".class") > 0) {
				// przygotowanie String do wczytania klasy
				sb = new StringBuffer();
				sb.append("algorithm").append(".");
				sb.append(file.getName().substring(0,
						file.getName().indexOf(".class")));

				Class<? extends Algorithm> algorithmClass;
				try {
					algorithmClass = (Class<? extends Algorithm>) Class
							.forName(sb.toString());
					algorithmClassList.add(algorithmClass);
				} catch (ClassNotFoundException e) {

				}

			}
		}
		return algorithmClassList;
	}

	/**
	 * przygotowuje mapy sygnalow kupna i sprzedazy dla wszystkich wylosowanych
	 * algorytmow
	 */
	private void prepareTransactionSignalMap(GpwCompany company) {
		List<Quotation> priceList = quotationService
				.findByGpwCompanyWithDateLimit(company, startDate, endDate);
		List<Quotation> openTransactionSignalList;
		List<Quotation> closeTransactionSignalList;
		// przygotowuje listy sygnalow otwrcia lub zamkniecia transakcji
		// mapowane po algorytmach
		for (GpwAlgorithm algorithm : algorithmVersionList) {
			openTransactionSignalList = new ArrayList<Quotation>();
			closeTransactionSignalList = new ArrayList<Quotation>();
			prepareTransactionSignals(algorithm, priceList,
					openTransactionSignalList, closeTransactionSignalList,
					company.getShortName());
			openTransactionSignalMap.put(algorithm, openTransactionSignalList);
			closeTransactionSignalMap
					.put(algorithm, closeTransactionSignalList);
		}

	}

	/** przygotowuje listy sygnalow kupna i sprzedazy */
	private void prepareTransactionSignals(GpwAlgorithm algorithm,
			List<Quotation> priceList,
			List<Quotation> openTransactionSignalList,
			List<Quotation> closeTransactionSignalList, String companyName) {
		try {
			// sprawdzam czy nie zostaly juz zapisane sygnaly dla danego
			// algorytmu, jezeli byly to zostana zaczytane i przekazane poprzez
			// argumenty metody
			if (loadReport(companyName, algorithm.toString(),
					openTransactionSignalList, closeTransactionSignalList))
				return;
		} catch (IOException e) {
			System.out.println("nieudane ladowanie raportu");
		}
		if (!algorithm.loadAlgorithmToTest(priceList))
			return;

		int maxLength = algorithm.getLengthListToLoad();
		for (int i = maxLength; i < priceList.size(); i++) {
			algorithm.putPrice(priceList.get(i));
			if (algorithm.openTransaction()) {
				if (i < priceList.size()) {
					openTransactionSignalList.add(priceList.get(i));
				}
			} else if (!algorithm.openTransaction()) {
			}

			if (algorithm.closeTransaction()) {
				if (i < priceList.size()) {
					closeTransactionSignalList.add(priceList.get(i));
				}
			} else if (!algorithm.closeTransaction()) {
			}
		}
		printReport(companyName, algorithm.toString(),
				openTransactionSignalList, closeTransactionSignalList);
	}

	/**
	 * sortuje wyniki symulacji testu wedlug skutecznosci od najwyzszej do
	 * najnizszej
	 */
	private void sortSimulationResult(List<GpwSimulationResult> resultList) {
		boolean sorted = false;

		while (!sorted) {
			sorted = true;
			for (int i = 1; i < resultList.size(); i++) {
				if (resultList.get(i - 1).getCapital()
						.compareTo(resultList.get(i).getCapital()) < 0) {
					GpwSimulationResult temp = resultList.get(i);
					resultList.set(i, resultList.get(i - 1));
					resultList.set(i - 1, temp);
					i--;
					sorted = false;
				}
			}
		}
		sorted = false;
		while (!sorted) {
			sorted = true;
			for (int i = 1; i < resultList.size(); i++) {
				if (resultList
						.get(i - 1)
						.getEfficiencyInPercentage()
						.compareTo(
								resultList.get(i).getEfficiencyInPercentage()) < 0) {
					GpwSimulationResult temp = resultList.get(i);
					resultList.set(i, resultList.get(i - 1));
					resultList.set(i - 1, temp);
					i--;
					sorted = false;
				}
			}
		}
	}

	/**
	 * wybiera najlepsza pare algrytmow na podstawie map sygnalow kupna i
	 * sprzedazy
	 */
	private List<GpwSimulationResult> chooseBestAlgorithm(String companyName) {
		List<GpwSimulationResult> resultList = new ArrayList<GpwSimulationResult>();
		GpwSimulationResult bestResult = null;
		GpwSimulationResult result;
		// dla wszystkich algorytmow jako algorytmy otwierajace
		for (GpwAlgorithm openAlgorithm : algorithmVersionList) {
			// dla wszystkich algorytmow jako algorytmy zamykajace
			for (GpwAlgorithm closeAlgorithm : algorithmVersionList) {
				result = countRateOfReturn(openAlgorithm, closeAlgorithm);
				if (result != null) {
					if (bestResult == null) {
						if (result.getCapital().compareTo(BigDecimal.ZERO) > 0) {
							resultList.add(result);
							bestResult = result;
							printReport(result, companyName);
						}
					} else if (bestResult.getCapital().compareTo(
							result.getCapital()) < 0
							&& result.getPositiveTransactionAmount() > 3) {
						resultList.add(result);
						bestResult = result;
						printReport(result, companyName);
					}
				}
			}
		}

		return resultList;
	}

	/**
	 * oblicza kapital koncowy dla pary algorytmow.<br/>
	 * <b>zasady:</b><br/>
	 * sygnal otwarcia gdy algorytm otwarcia generuje sugnal i algorytm
	 * zamkniecia nie generuje sygnalu<br/>
	 * sygnal zamkniecia gdy algorytm zamkniecia generuje sygnal
	 * */
	@SuppressWarnings("static-access")
	private GpwSimulationResult countRateOfReturn(GpwAlgorithm openAlgorithm,
			GpwAlgorithm closeAlgorithm) {
		System.out
				.println("*******************************************************");
		System.out.println(openAlgorithm + "\t" + closeAlgorithm);
		System.out
				.println("*******************************************************");

		GpwSimulationResult result = new GpwSimulationResult();
		result.setOpenAlgorithm(openAlgorithm);
		result.setCloseAlgorithm(closeAlgorithm);
		BigDecimal capital = new BigDecimal(this.startCapital.doubleValue());
		List<Quotation> openTransactionSignalList = openTransactionSignalMap
				.get(openAlgorithm);
		List<Quotation> closeTransactionSignalList = closeTransactionSignalMap
				.get(closeAlgorithm);
		// gdy ktoras z list syganlow jest pusta, przyjmowane sa warunki
		// niespelniajace wygranej
		// proces symulacji zostaje przerwany, bo zadna z transakcji nie
		// zostanie zakonczona w pelni
		if (openTransactionSignalList.size() == 0
				|| closeTransactionSignalList.size() == 0) {
			result.setCapital(BigDecimal.ZERO);
			return result;
		}
		// liczba zakupionych jednostek funduszu
		BigDecimal shareAmount;
		// wartosc sprzedanych jednostek funduszu
		BigDecimal shareValue;
		int closeIndex = 0;
		transactionProcess: for (int openIndex = 0; openIndex < openTransactionSignalList
				.size(); openIndex++) {
			// szukam sygnalu zamkniecia ktory nie jest wczesniej niz sygnal
			// otwarcia
			// closeSignal.date>=openSigaml.date
			while (closeTransactionSignalList
					.get(closeIndex)
					.getDate()
					.compareTo(
							openTransactionSignalList.get(openIndex).getDate()) < 0) {
				closeIndex++;
				if (!(closeIndex < closeTransactionSignalList.size())) {
					break transactionProcess;
				}
			}
			// gdy tego samego dnia wygenerowany jest syganl otwarcia i
			// zamkniecia transakcja nie moze zostac otwarta
			if (closeTransactionSignalList
					.get(closeIndex)
					.getDate()
					.compareTo(
							openTransactionSignalList.get(openIndex).getDate()) == 0) {
				continue;
			}
			// gdy daty sa rozne (sygnal zamkniecia jest pozniej niz sygnal
			// otwarcia
			// obliczana transakcja dla kursu otwarcia z listy sygnalow
			// otwierajacych i dla kursu zamkniecia z listy sygnalow
			// zamykajacych
			else {
				if ((openIndex) < openTransactionSignalList.size()
						&& (closeIndex) < closeTransactionSignalList.size()) {
					shareAmount = calculateShareAmount(capital,
							openTransactionSignalList.get(openIndex).getValue());
					BigDecimal buyValue = shareAmount
							.multiply(openTransactionSignalList.get(openIndex)
									.getValue());
					BigDecimal rest = capital.subtract(buyValue);
					shareValue = caluculateShareValue(shareAmount,
							closeTransactionSignalList.get(closeIndex)
									.getValue());
					if (capital.compareTo(shareValue) < 0) {
						result.positiveTransaction();
					} else {
						result.negativeTransaction();
					}
					capital = rest.add(shareValue);

					// szukam sygnalu otwarcia ktory nie jest wczesniej niz
					// ostatnio zaobserwowany
					// openSignal.date>=closeSignal.date
					while (openTransactionSignalList
							.get(openIndex)
							.getDate()
							.compareTo(
									closeTransactionSignalList.get(closeIndex)
											.getDate()) < 0) {
						openIndex++;
						if (!(openIndex < openTransactionSignalList.size()))
							break transactionProcess;
					}
				} else {
					break transactionProcess;
				}
			}
		}
		result.setCapital(capital);
		return result;
	}

	/**
	 * oblicza liczbe jednostek funduszu jaka mozna kupic za okreslona cene.
	 * Jednostki funduszy mozna kupowaæ w ilosciach ulamkowych
	 * 
	 * @param capital
	 *            wartosc za ktora beda kupowane jednostki funduszu
	 * @param price
	 *            cena jednostki funduszu
	 */
	public BigDecimal calculateShareAmount(BigDecimal capital, BigDecimal price) {
		if (capital.compareTo(BigDecimal.ZERO) > 0
				&& price.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal shareAmount = capital.divide(price,
					MathContext.DECIMAL64);
			BigDecimal result = new BigDecimal(shareAmount.intValue());
			return result;
		} else
			return BigDecimal.ZERO;
	}

	/**
	 * oblicza wartosc jednostek funduszu na podstawie ceny
	 * 
	 * @param amount
	 *            ilosc jednostek
	 * @param price
	 *            cena jednostki funduszu
	 */
	private BigDecimal caluculateShareValue(BigDecimal amount, BigDecimal price) {
		BigDecimal shareValue = price.multiply(amount);
		return shareValue;
	}

	/** utrwala wybor algorytmow przez uzytkownika */
	public void changeChoosenAlgorithm(GpwSimulationResult simulationResult,
			GpwCompany company) {
		desactivateAlgorithm(company);
		activateAlgoritms(simulationResult, company);
	}

	/** dezaktywuje wybrane algorytmy dla funduszu */
	public void desactivateAlgorithm(GpwCompany company) {
		GpwAlgorithm algorithm = gpwAlgorithmService
				.findActiveOpenAlgorithm(company);
		if (algorithm != null) {
			algorithm.setActive(false);
			algorithm.setDesactivateDate(new GregorianCalendar());
			gpwAlgorithmService.saveOrUpdate(algorithm);
		}

		algorithm = gpwAlgorithmService.findActiveCloseAlgorithm(company);
		if (algorithm != null) {
			algorithm.setActive(false);
			algorithm.setDesactivateDate(new GregorianCalendar());
			gpwAlgorithmService.saveOrUpdate(algorithm);
		}
	}

	/**
	 * aktywuje algorytmu dla danego funduszu ustawia relacje, wylicza potrzebne
	 * pola i zapisuje do bazy danych
	 */
	private void activateAlgoritms(GpwSimulationResult simulationResult,
			GpwCompany company) {
		if (simulationResult == null)
			return;
		GpwAlgorithm openingAlgorithm = new GpwAlgorithm(
				simulationResult.getOpenAlgorithm());
		openingAlgorithm.loadDataFromAlgorithm();
		openingAlgorithm.setCompany(company);
		openingAlgorithm.setActive(true);
		openingAlgorithm.setOpeningAlgorithm(true);
		openingAlgorithm.setCreateDate(new GregorianCalendar());
		gpwAlgorithmService.saveOrUpdate(openingAlgorithm);
		company.setOpeningAlgorithm(openingAlgorithm);

		GpwAlgorithm closingAlgorithm = new GpwAlgorithm(
				simulationResult.getCloseAlgorithm());
		closingAlgorithm.loadDataFromAlgorithm();
		closingAlgorithm.setCompany(company);
		closingAlgorithm.setActive(true);
		closingAlgorithm.setOpeningAlgorithm(false);
		closingAlgorithm.setCreateDate(new GregorianCalendar());
		gpwAlgorithmService.saveOrUpdate(closingAlgorithm);
		company.setClosingAlgorithm(closingAlgorithm);

		SignalWeight weight = company.getWeight();
		if (weight == null) {
			weight = new SignalWeight();
			company.setWeight(weight);
		}
		weight.setEfficiency(simulationResult.getEfficiencyInPercentage());
		weight.setRateOfProfit(simulationResult.getRateOfProfit(startCapital));
		gpwCompanyService.saveOrUpdate(company);
	}

	// ***********************************************************************
	// ************************** RAPORTY ********************************
	// ***********************************************************************

	/**
	 * zapisuje do pliku raporty dla sygnalow otwarcia i zamkniecia pozycji dla
	 * danego funduszu w formacie xls
	 */
	private void printReport(String companyName, String algorithmName,
			List<Quotation> openTransactionSignalList,
			List<Quotation> closeTransactionSignalList) {

		if (companyName == null || algorithmName == null
				|| openTransactionSignalList == null
				|| closeTransactionSignalList == null)
			return;
		StringBuffer buffer;
		File file = new File("configs/reports/gpw/open_transaction_signal");
		file.mkdirs();

		if (openTransactionSignalList.size() > 0) {
			buffer = new StringBuffer();
			buffer.append("configs/reports/gpw/open_transaction_signal/");
			buffer.append(companyName).append("_").append(algorithmName)
					.append(".xls");
			try {
				FileWriter writer = new FileWriter(buffer.toString());
				for (Quotation fund : openTransactionSignalList) {
					buffer = new StringBuffer();
					buffer.append(fund.getId()).append(";");
					buffer.append(
							DateUtils.convertToStringFormat(fund.getDate(),
									DateFormat.YYYY_MM_DD)).append(";");
					buffer.append(fund.getValue()).append("\n");
					writer.write(buffer.toString());
				}
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		file = new File("configs/reports/gpw/close_transaction_signal");
		file.mkdirs();
		if (closeTransactionSignalList.size() > 0) {
			buffer = new StringBuffer();
			buffer.append("configs/reports/gpw/close_transaction_signal/");
			buffer.append(companyName).append("_").append(algorithmName)
					.append(".xls");
			try {
				FileWriter writer = new FileWriter(buffer.toString());
				for (Quotation fund : closeTransactionSignalList) {
					buffer = new StringBuffer();
					buffer.append(fund.getId()).append(";");
					buffer.append(
							DateUtils.convertToStringFormat(fund.getDate(),
									DateFormat.YYYY_MM_DD)).append(";");
					buffer.append(fund.getValue()).append("\n");
					writer.write(buffer.toString());
				}
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/** zapisuje do pliku raport wyniku symulacji algorytmu (testu) */
	private void printReport(GpwSimulationResult result, String fundName) {

		File file = new File("configs/reports/gpw/test_result");
		file.mkdirs();
		StringBuffer buffer = new StringBuffer();
		buffer.append("configs/reports/gpw/test_result/");
		buffer.append(fundName).append(".xls");
		int sum = result.getPositiveTransactionAmount()
				+ result.getNegaticeTransactionAmount();
		BigDecimal efficiency = new BigDecimal(sum);
		efficiency = new BigDecimal(result.getPositiveTransactionAmount())
				.divide(efficiency, MathContext.DECIMAL32);
		try {
			FileWriter writer = new FileWriter(buffer.toString(), true);
			buffer = new StringBuffer();
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

	/**
	 * sprawdza i pobiera raport sygnalow otwarcia i zamkniecia<br/>
	 * 
	 * @return true gdy znalazl zapisany raport, false gdy nie znalazl raportu
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	private boolean loadReport(String companyName, String algorithmName,
			List<Quotation> openTransactionSignalList,
			List<Quotation> closeTransactionSignalList) throws IOException {
		if (companyName == null || algorithmName == null)
			return false;

		StringBuffer buffer = new StringBuffer();
		buffer.append("configs/reports/gpw/open_transaction_signal/");
		buffer.append(companyName).append("_").append(algorithmName)
				.append(".xls");
		File file = new File(buffer.toString());
		if (file.createNewFile()) {
			file.delete();
			return false;
		}

		Scanner reader = new Scanner(file);
		StringTokenizer token;
		// GregorianCalendar date;
		Quotation value;
		while (reader.hasNextLine()) {
			token = new StringTokenizer(reader.nextLine(), ";", false);
			value = new Quotation();
			value.setId(new Long(token.nextToken()));
			value.setDate(DateUtils.convertToDate(token.nextToken()));
			value.setValue(new BigDecimal(token.nextToken()));
			openTransactionSignalList.add(value);
		}

		buffer = new StringBuffer();
		buffer.append("configs/reports/gpw/close_transaction_signal/");
		buffer.append(companyName).append("_").append(algorithmName)
				.append(".xls");
		file = new File(buffer.toString());
		if (file.createNewFile())
			return false;

		reader = new Scanner(file);

		while (reader.hasNextLine()) {
			token = new StringTokenizer(reader.nextLine(), ";", false);
			value = new Quotation();
			value.setId(new Long(token.nextToken()));
			value.setDate(DateUtils.convertToDate(token.nextToken()));
			value.setValue(new BigDecimal(token.nextToken()));
			closeTransactionSignalList.add(value);
		}
		return true;
	}

	private void clearReports() {
		File file = new File("configs/reports/gpw/open_transaction_signal");
		File[] fileTable = file.listFiles();
		for (File report : fileTable) {
			report.delete();
		}

		file = new File("configs/reports/gpw/close_transaction_signal");
		fileTable = file.listFiles();
		for (File report : fileTable) {
			report.delete();
		}
	}

	private void moveMouse() {
		new Thread() {
			public void run() {
				try {
					Robot robot = new Robot();
					Random random = new Random();
					int x = random.nextInt(1000);
					int y = random.nextInt(1000);
					robot.mouseMove(x, y);
				} catch (AWTException e) {
					// throw new RuntimeException(e);
				}
			}
		}.start();
	}

}
