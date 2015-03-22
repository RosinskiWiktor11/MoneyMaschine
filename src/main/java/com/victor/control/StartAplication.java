package com.victor.control;

import javax.annotation.ManagedBean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

import com.victor.config.ConfigManager;

@ManagedBean
@Repository
public class StartAplication {

	public static void main(String... args) {
		StartAplication.run();
	}

	@SuppressWarnings("resource")
	public static void run() {
		System.out.println("start");
		ApplicationContext context = new AnnotationConfigApplicationContext(
				ConfigManager.class);
		MainController mainController = context.getBean(MainController.class);
		mainController.startApplication();

		System.out.println("end");
	}

}
