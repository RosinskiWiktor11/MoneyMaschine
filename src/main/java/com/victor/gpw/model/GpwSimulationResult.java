package com.victor.gpw.model;

import java.math.BigDecimal;
import java.math.MathContext;

import com.victor.entity.GpwAlgorithm;
import com.victor.entity.SkandiaAlgorithm;
import com.victor.skandia.model.SkandiaSimulationResult;

/**
 * @author Wiktor Rosinski
 *
 */
public class GpwSimulationResult  implements SimulationResult{
	/** liczba pozytwnych transakcji */
	private int positiveTransactionAmount;
	/** liczba negatywnych transakcji */
	private int negaticeTransactionAmount;
	/** kapital koncowy */
	private BigDecimal capital;
	/** algorytm otwierajacy */
	private GpwAlgorithm openAlgorithm;
	/** algorytm zamykajacy */
	private GpwAlgorithm closeAlgorithm;
	
	public GpwSimulationResult(){
		positiveTransactionAmount=0;
		negaticeTransactionAmount=0;
	}
	
	public void positiveTransaction(){
		positiveTransactionAmount++;
	}
	public void negativeTransaction(){
		negaticeTransactionAmount++;
	}
	
	/** oblicza efektownosc transakcji pozytywnych [pozytywne/(pozytywne+negatywne)] */
	public BigDecimal getEfficiencyInPercentage(){
		BigDecimal transactionSum=new BigDecimal(positiveTransactionAmount+negaticeTransactionAmount);
		BigDecimal efficiency=new BigDecimal(positiveTransactionAmount).divide(transactionSum, new MathContext(4));
		efficiency=efficiency.multiply(new BigDecimal(100));
		return efficiency;
	}
	
	public BigDecimal getRateOfProfit(BigDecimal beginingCapital){
		BigDecimal substract=capital.subtract(beginingCapital);
		BigDecimal rateOfProfit=substract.divide(beginingCapital, new MathContext(4));
		return rateOfProfit.multiply(new BigDecimal(100));
	}
	

	/**{@link SkandiaSimulationResult#positiveTransactionAmount}*/
	public int getPositiveTransactionAmount() {
		return positiveTransactionAmount;
	}
	/**{@link SkandiaSimulationResult#positiveTransactionAmount}*/
	public void setPositiveTransactionAmount(int positiveTransactionAmount) {
		this.positiveTransactionAmount = positiveTransactionAmount;
	}
	/**{@link SkandiaSimulationResult#negaticeTransactionAmount}*/
	public int getNegaticeTransactionAmount() {
		return negaticeTransactionAmount;
	}
	/**{@link SkandiaSimulationResult#negaticeTransactionAmount}*/
	public void setNegaticeTransactionAmount(int negaticeTransactionAmount) {
		this.negaticeTransactionAmount = negaticeTransactionAmount;
	}
	/**{@link SkandiaSimulationResult#capital}*/
	public BigDecimal getCapital() {
		return capital;
	}
	/**{@link SkandiaSimulationResult#capital}*/
	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	/**{@link GpwSimulationResult#openAlgorithm}*/
	public GpwAlgorithm getOpenAlgorithm() {
		return openAlgorithm;
	}

	/**{@link GpwSimulationResult#openAlgorithm}*/
	public void setOpenAlgorithm(GpwAlgorithm openAlgorithm) {
		this.openAlgorithm = openAlgorithm;
	}

	/**{@link GpwSimulationResult#closeAlgorithm}*/
	public GpwAlgorithm getCloseAlgorithm() {
		return closeAlgorithm;
	}

	/**{@link GpwSimulationResult#closeAlgorithm}*/
	public void setCloseAlgorithm(GpwAlgorithm closeAlgorithm) {
		this.closeAlgorithm = closeAlgorithm;
	}

	
	
	
}