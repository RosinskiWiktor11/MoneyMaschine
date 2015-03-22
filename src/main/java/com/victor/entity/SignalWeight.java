package com.victor.entity;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

/**
 * @author Wiktor Rosinski
 * 	waga algorytmu otwierajacego dla funduszu. Okresla jak istotny jest sygnal otwarcia transakcji
 *
 */
@Embeddable
public class SignalWeight {
	
	private BigDecimal efficiency;
	private BigDecimal rateOfProfit;
	
	
	/**{@link SignalWeight#efficiency}*/
	public BigDecimal getEfficiency() {
		return efficiency;
	}
	/**{@link SignalWeight#efficiency}*/
	public void setEfficiency(BigDecimal efficiency) {
		this.efficiency = efficiency;
	}
	/**{@link SignalWeight#rateOfProfit}*/
	public BigDecimal getRateOfProfit() {
		return rateOfProfit;
	}
	/**{@link SignalWeight#rateOfProfit}*/
	public void setRateOfProfit(BigDecimal rateOfProfit) {
		this.rateOfProfit = rateOfProfit;
	}
	
	

}
