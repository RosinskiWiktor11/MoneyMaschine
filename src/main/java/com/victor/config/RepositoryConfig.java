package com.victor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.victor.dao.ActivityDAO;
import com.victor.dao.ActivityDAOImpl;
import com.victor.dao.CapitalValueDAO;
import com.victor.dao.CapitalValueDAOImpl;
import com.victor.dao.CurrencyDAO;
import com.victor.dao.CurrencyDAOImpl;
import com.victor.dao.CustomerDAO;
import com.victor.dao.CustomerDAOImpl;
import com.victor.dao.CustomerGpwTransactionDAO;
import com.victor.dao.CustomerGpwTransactionDAOImpl;
import com.victor.dao.CustomerPortfolioDAO;
import com.victor.dao.CustomerPortfolioDAOImpl;
import com.victor.dao.FundActivityDAO;
import com.victor.dao.FundActivityDAOImpl;
import com.victor.dao.FundValueDAO;
import com.victor.dao.FundValueDAOImpl;
import com.victor.dao.GpwAlgorithmDAO;
import com.victor.dao.GpwAlgorithmDAOImpl;
import com.victor.dao.GpwCompanyDAO;
import com.victor.dao.GpwCompanyDAOImpl;
import com.victor.dao.GpwTransactionDAO;
import com.victor.dao.GpwTransactionDAOImpl;
import com.victor.dao.InvestmentFundDAO;
import com.victor.dao.InvestmentFundDAOImpl;
import com.victor.dao.PaymentDAO;
import com.victor.dao.PaymentDAOImpl;
import com.victor.dao.PortfolioDAO;
import com.victor.dao.PortfolioDAOImpl;
import com.victor.dao.PortfolioFundDAO;
import com.victor.dao.PortfolioFundDAOImpl;
import com.victor.dao.QuotationDAO;
import com.victor.dao.QuotationDAOImpl;
import com.victor.dao.SkandiaAlgorithmDAO;
import com.victor.dao.SkandiaAlgorithmDAOImpl;

/**
 * @author Wiktor<br/>
 *         klasa konfiguracji dla bean'ow DAO
 */
@Configuration
public class RepositoryConfig {

	@Bean
	public SkandiaAlgorithmDAO skandiaAlgorithmDAOImpl() {
		return new SkandiaAlgorithmDAOImpl();
	}

	@Bean
	public CapitalValueDAO capitalValueDAOImpl() {
		return new CapitalValueDAOImpl();
	}

	@Bean
	public CustomerDAO customerDAOImpl() {
		return new CustomerDAOImpl();
	}

	@Bean
	public CustomerPortfolioDAO customerPortfolioDAOImpl() {
		return new CustomerPortfolioDAOImpl();
	}

	@Bean
	public FundValueDAO fundValueDAOImpl() {
		return new FundValueDAOImpl();
	}

	@Bean
	public InvestmentFundDAO investmentFundDAOImpl() {
		return new InvestmentFundDAOImpl();
	}

	@Bean
	public PaymentDAO paymentDAOImpl() {
		return new PaymentDAOImpl();
	}

	@Bean
	public PortfolioDAO portfolioDAOImpl() {
		return new PortfolioDAOImpl();
	}

	@Bean
	public PortfolioFundDAO portfolioFundDAOImpl() {
		return new PortfolioFundDAOImpl();
	}

	@Bean
	public CurrencyDAO currencyDAOImpl() {
		return new CurrencyDAOImpl();
	}

	@Bean
	public ActivityDAO activityDAOImpl() {
		return new ActivityDAOImpl();
	}

	@Bean
	public FundActivityDAO activityFundDAOImpl() {
		return new FundActivityDAOImpl();
	}
	
	@Bean
	public GpwAlgorithmDAO gpwAlgorithmDAOImpl(){
		return new GpwAlgorithmDAOImpl();
	}
	
	@Bean
	public QuotationDAO quotationDAOImpl(){
		return new QuotationDAOImpl();
	}
	
	@Bean 
	public CustomerGpwTransactionDAO customerGpwTransactionDAOImpl(){
		return new CustomerGpwTransactionDAOImpl();
	}
	
	@Bean 
	public GpwTransactionDAO gpwTransactionDAOImpl(){
		return new GpwTransactionDAOImpl();
	}
	
	@Bean
	public GpwCompanyDAO gpwCompanyDAOImpl(){
		return new GpwCompanyDAOImpl();
	}
	
}
