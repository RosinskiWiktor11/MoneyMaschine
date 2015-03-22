package com.victor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.victor.service.ActivityService;
import com.victor.service.CapitalValueService;
import com.victor.service.CurrencyService;
import com.victor.service.CustomerGpwTransactionService;
import com.victor.service.CustomerPortfolioService;
import com.victor.service.CustomerService;
import com.victor.service.FundActivityService;
import com.victor.service.FundValueService;
import com.victor.service.GpwAlgorithmService;
import com.victor.service.GpwCompanyService;
import com.victor.service.GpwTransactionService;
import com.victor.service.InvestmentFundService;
import com.victor.service.PaymentService;
import com.victor.service.PortfolioFundService;
import com.victor.service.PortfolioService;
import com.victor.service.QuotationService;
import com.victor.service.SkandiaAlgorithmService;

/**
 * @author Wiktor<br/>
 *         klasa konfiguracji dla bean'ow serwisow
 */
@Configuration
public class ServiceConfig {
	

	@Bean
	public SkandiaAlgorithmService skandiaAlgorithmService() {
		return new SkandiaAlgorithmService();
	}

	@Bean
	public CapitalValueService capitalValueService() {
		return new CapitalValueService();
	}

	@Bean
	public CustomerService customerService() {
		return new CustomerService();
	}

	@Bean
	public CustomerPortfolioService customerPortfolioService() {
		return new CustomerPortfolioService();
	}

	@Bean
	public FundValueService fundValueService() {
		return new FundValueService();
	}

	@Bean
	public InvestmentFundService investmentFundService() {
		return new InvestmentFundService();
	}

	@Bean
	public PaymentService paymentService() {
		return new PaymentService();
	}

	@Bean
	public PortfolioService portfolioService() {
		return new PortfolioService();
	}

	@Bean
	public PortfolioFundService portfolioFundService() {
		return new PortfolioFundService();
	}

	@Bean
	public CurrencyService currencyService() {
		return new CurrencyService();
	}

	@Bean
	public ActivityService activityService() {
		return new ActivityService();
	}

	@Bean
	public FundActivityService fundActivityService() {
		return new FundActivityService();
	}

	@Bean
	public GpwAlgorithmService gpwAlgorithmService(){
		return new GpwAlgorithmService();
	}
	
	@Bean
	public QuotationService quotationService(){
		return new QuotationService();
	}
	
	@Bean
	public CustomerGpwTransactionService customerGpwTransactionService(){
		return new CustomerGpwTransactionService();
	}
	
	@Bean
	public GpwTransactionService gpwTransactionService(){
		return new GpwTransactionService();
	}
	
	@Bean
	public GpwCompanyService gpwCompanyService(){
		return new GpwCompanyService();
	}
	
}
