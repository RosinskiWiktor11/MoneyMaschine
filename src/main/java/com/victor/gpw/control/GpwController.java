package com.victor.gpw.control;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.springframework.stereotype.Controller;

import com.victor.config.ViewControllerConfig;
import com.victor.control.LoginController;
import com.victor.control.ViewController;
import com.victor.entity.Customer;
import com.victor.entity.CustomerGpwTransaction;
import com.victor.entity.GpwCompany;
import com.victor.entity.GpwTransaction;
import com.victor.gpw.model.GpwSimulationResult;
import com.victor.gpw.view.GpwPanel;
import com.victor.service.CurrencyService;
import com.victor.skandia.model.TestResult;
import com.victor.utils.DateFormat;
import com.victor.utils.DateUtils;

/**
 * @author Wiktor Rosinski<br/>
 *
 */
@Controller
public class GpwController extends ViewController<GpwPanel> {

	private GpwParserManager parser;
	private GpwAlgorithmManager algorithmManager;
	private GpwPortfolioManager portfolioManager;
	private GpwChartManager chartManager;

	/**
	 * {@link GpwController}
	 * 
	 * @param panel
	 */
	public GpwController(GpwPanel panel) {
		super(panel);
		loadControllers();

	}

	/**
	 * importuje z zakresie dat
	 */
	public void importData(String dateFrom, String dateUntil, boolean isChanged) {
		if (dateFrom.equals(dateUntil)) {
			GregorianCalendar calendar = DateUtils.convertToDateFormat(
					dateFrom, DateFormat.DD_MM_YYYY);
			parser.importOneDayCompanyQuotation(calendar);
		} else {
			GregorianCalendar startDate = DateUtils.convertToDateFormat(
					dateFrom, DateFormat.DD_MM_YYYY);
			GregorianCalendar endDate = DateUtils.convertToDateFormat(
					dateUntil, DateFormat.DD_MM_YYYY);
			parser.importCompanyQuotationBetween(startDate, endDate);
		}
	}

	/**
	 * @return
	 */
	public List<GpwCompany> getActiveGpwCompanyList() {
		return algorithmManager.getActiveCompanies();
	}

	/**
	 * @param fund
	 * @return
	 */
	public TestResult makeAlgorithmTest(GpwCompany company) {
		return algorithmManager.makeAlgorithmTest(company);
	}

	public void changeChoosenAlgorithm(GpwSimulationResult simulationResult,
			GpwCompany company) {
		algorithmManager.changeChoosenAlgorithm(simulationResult, company);
	}

	/**
	 * @param fund
	 * @param dateFrom
	 * @param dateUntil
	 * @return
	 */
	public JPanel showChartForFundWithSignals(GpwCompany company, GregorianCalendar dateFrom, GregorianCalendar dateUntil) {
		return chartManager.showChartForFundAlgorithms(company, dateFrom, dateUntil);
		
	}

	/**
	 * @return
	 */
	public List<GpwCompany> generateOpenTransactionSingals() {
		return portfolioManager.generateOpenTransactionSingals();
	}

	/**
	 * @param portfolioFundList
	 * @param customerType
	 */
	public void createNewPortfolio(List<CustomerGpwTransaction> customerTransactionList) {	
		portfolioManager.createNewPortfolio(customerTransactionList);
	}

	/**
	 * 
	 */
	public void importPortfolioData() {
		JOptionPane.showOptionDialog(null, "Brak funkcjonalnoœci",
				"Ten modu³ nie obs³uguje funkcjonalnoœci",
				JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				new String[] { "OK" }, null);
	}

	/**
	 * @return
	 */
	public List<GpwCompany>[] checkBoughtCompanies() {
		return portfolioManager.checkBoughtCompanies();
	}

	private void loadControllers() {
		parser = ViewControllerConfig.gpwParserManager;
		algorithmManager = ViewControllerConfig.gpwAlgorithmManger;
		portfolioManager = ViewControllerConfig.gpwPortfolioManager;
		chartManager=ViewControllerConfig.gpwChartManager;
	}

}
