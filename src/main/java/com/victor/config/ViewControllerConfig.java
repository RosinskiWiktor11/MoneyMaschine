package com.victor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.victor.control.MainController;
import com.victor.gpw.control.GpwAlgorithmManager;
import com.victor.gpw.control.GpwChartManager;
import com.victor.gpw.control.GpwParserManager;
import com.victor.gpw.control.GpwPortfolioManager;
import com.victor.skandia.control.SkandiaAlgorithManager;
import com.victor.skandia.control.SkandiaChartManager;
import com.victor.skandia.control.SkandiaParserController;
import com.victor.skandia.control.SkandiaPorfolioManager;

/**
 * @author Wiktor<br/>
 *         klasa konfiguracji dla bean'ow kontrolerow widokow
 *
 */
@Configuration
public class ViewControllerConfig {

	public static SkandiaParserController skandiaParser;
	public static SkandiaAlgorithManager algorithmManager;
	public static SkandiaPorfolioManager skandiaPorfolioManager;
	public static SkandiaChartManager skandiaChartManager;

	public static GpwParserManager gpwParserManager;
	public static GpwAlgorithmManager gpwAlgorithmManger;
	public static GpwPortfolioManager gpwPortfolioManager;
	public static GpwChartManager gpwChartManager;
	
	@Bean
	public MainController mainController() {
		return new MainController();
	}

	@SuppressWarnings("static-access")
	@Bean
	public SkandiaParserController skandiaParser() {
		this.skandiaParser = new SkandiaParserController();
		return skandiaParser;
	}
	
	@SuppressWarnings("static-access")
	@Bean
	public SkandiaAlgorithManager skandiaAlgorithManager(){
		this.algorithmManager=new SkandiaAlgorithManager();
		return algorithmManager;
	}
	
	@SuppressWarnings("static-access")
	@Bean
	public SkandiaPorfolioManager skandiaPorfolioManager(){
		this.skandiaPorfolioManager=new SkandiaPorfolioManager();
		return skandiaPorfolioManager;
	}
	
	@SuppressWarnings("static-access")
	@Bean
	public SkandiaChartManager skandiaChartManager(){
		this.skandiaChartManager=new SkandiaChartManager();
		return skandiaChartManager;
	}
	
	@SuppressWarnings("static-access")
	@Bean
	public GpwParserManager gpwParserManager(){
		this.gpwParserManager=new GpwParserManager();
		return this.gpwParserManager;
	}

	@SuppressWarnings("static-access")
	@Bean
	public GpwAlgorithmManager gpwAlgorithmManager(){
		this.gpwAlgorithmManger=new GpwAlgorithmManager();
		return this.gpwAlgorithmManger;
	}
	
	@SuppressWarnings("static-access")
	@Bean
	public GpwPortfolioManager gpwPortfolioManager(){
		this.gpwPortfolioManager=new GpwPortfolioManager();
		return this.gpwPortfolioManager;
	}
	
	@SuppressWarnings("static-access")
	@Bean
	public GpwChartManager gpwChartManager(){
		this.gpwChartManager=new GpwChartManager();
		return this.gpwChartManager;
	}
}
