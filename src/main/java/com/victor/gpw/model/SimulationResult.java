package com.victor.gpw.model;

import java.math.BigDecimal;

/**
 * @author Witor Rosinski
 *
 */
public interface SimulationResult {

	/** oblicza efektownosc transakcji pozytywnych [pozytywne/(pozytywne+negatywne)] */
	public BigDecimal getEfficiencyInPercentage();
	public BigDecimal getRateOfProfit(BigDecimal beginingCapital);
	public int getPositiveTransactionAmount();
	public void setPositiveTransactionAmount(int positiveTransactionAmount);
	public int getNegaticeTransactionAmount();
	public void setNegaticeTransactionAmount(int negaticeTransactionAmount);
	public BigDecimal getCapital();
	public void setCapital(BigDecimal capital);
	
}
