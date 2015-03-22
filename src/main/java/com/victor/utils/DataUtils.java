package com.victor.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.victor.control.LoginController;
import com.victor.entity.Customer;
import com.victor.service.CustomerService;
import com.victor.skandia.model.CustomerGroupType;

/**
 * @author Wiktor Rosinski
 *
 */
public class DataUtils {

	/** stopa zwrotu calkowita */
	public static BigDecimal calculateRateOfProfit(BigDecimal beginCapital, BigDecimal finalCapital){
		BigDecimal substract=finalCapital.subtract(beginCapital);
		BigDecimal rateOfProfit=substract.divide(beginCapital, new MathContext(4));
		return rateOfProfit.multiply(new BigDecimal(100));
	}
	
	/** stopa zwrotu przeliczona na rok */
	public static BigDecimal calculateRateOfProfit(BigDecimal beginCapital, BigDecimal finalCapital, int dayAmount){
		BigDecimal rateOfProfit=calculateRateOfProfit(beginCapital, finalCapital);
		rateOfProfit.divide(new BigDecimal(dayAmount), new MathContext(2));
		return rateOfProfit.multiply(new BigDecimal(365));
	}
	
	public static List<Customer> getCustomers(CustomerGroupType customerGroupType, CustomerService customerService){
		List<Customer> resultList;
		switch(customerGroupType){
		case ALL:
			return customerService.findAll();
		case ALL_MY_CUSTOMERS:
			return customerService.findByAgent(LoginController.getLoggedUser());
		case ME:
			resultList=new ArrayList<Customer>();
			resultList.add(LoginController.getLoggedUser());
			return resultList;
		}
		return null;
	}
	

}
