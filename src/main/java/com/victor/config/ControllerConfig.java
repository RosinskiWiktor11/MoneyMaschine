package com.victor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.victor.control.LoginController;

/**
 * @author Wiktor<br/>
 *         klasa konfiguracji dla bean'ow kontrolerow encji
 *
 */
@Configuration
public class ControllerConfig {

	public static LoginController loginController;
	
	@Bean
	public LoginController loginController(){
		loginController= new LoginController();
		return loginController;
	}
}
