package com.victor.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.victor.entity.Customer;
import com.victor.service.CustomerService;
import com.victor.view.LoginFrame;
import com.victor.view.Window;

/**
 * @author Witkor Rosinski
 *
 */
@Controller
public class LoginController {

	@Autowired
	CustomerService customerService;
	
	private static Customer loggedUser;
	
	
	
	public void login(){
	new LoginFrame(this).setVisible(true);
	}
	
	public boolean loginProcess(String login, String password){
		Customer customer= customerService.loginProcess(login, password);
		if(customer!=null){
			loggedUser=customer;
			new Window();
			return true;
		} else{
			loggedUser=null;
			return false;
		}
	}
	
	public static Customer getLoggedUser(){
		return loggedUser;
	}
}
