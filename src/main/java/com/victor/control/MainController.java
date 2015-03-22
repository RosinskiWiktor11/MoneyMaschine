package com.victor.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author Wiktor kotroler glownego okna
 */
@Controller
public class MainController {

	@Autowired
	LoginController loginController;

	public void startApplication() {
		loginController.login();
	}
}
 		