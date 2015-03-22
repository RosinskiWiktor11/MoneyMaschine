package com.victor.skandia.model;

import java.util.GregorianCalendar;

/**
 * @author Wiktor Rosinski
 *
 */
public interface DateChangeInterface {

	public void setStartDate(GregorianCalendar date);
	public void setEndDate(GregorianCalendar date);
	public void run();
}
