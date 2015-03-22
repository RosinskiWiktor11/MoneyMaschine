package com.victor.skandia.model;

import java.math.BigDecimal;
import java.util.List;

import com.victor.entity.FinancialInstrument;
import com.victor.entity.GpwCompany;
import com.victor.entity.InvestmentFund;
import com.victor.gpw.model.SimulationResult;

/**
 * @author Wiktor Rosinski
 *
 */
public class TestResult {

	/** wybrany algorytm dla funduszu przez uzytkownika lub przez system */
	private SimulationResult choosenResult;
	/** lista pozostalych wynikow testu */
	private List<? extends SimulationResult> restResltList;
	/** kapital poczatkowy */
	private BigDecimal beningCapital;
	/** fundusz dla ktorego testy byly przeprowadzone */
	private FinancialInstrument investmentFund;
	
	
	/**{@link TestResult#restResltList}*/
	public List<? extends SimulationResult> getRestResltList() {
		return restResltList;
	}
	/**{@link TestResult#restResltList}*/
	public void setRestResltList(List<? extends SimulationResult> restResltList) {
		this.restResltList = restResltList;
	}
	/**{@link TestResult#choosenResult}*/
	public SimulationResult getChoosenResult() {
		return choosenResult;
	}
	/**{@link TestResult#choosenResult}*/
	public void setChoosenResult(SimulationResult choosenResult) {
		this.choosenResult = choosenResult;
	}
	/**{@link TestResult#investmentFund}*/
	public InvestmentFund getInvestmentFund() {
		return (InvestmentFund) investmentFund;
	}
	public GpwCompany getCompany(){
		return (GpwCompany) investmentFund;
	}
	/**{@link TestResult#investmentFund}*/
	public void setInvestmentFund(InvestmentFund investmentFund) {
		this.investmentFund = investmentFund;
	}
	public void setCompany(GpwCompany company){
		this.investmentFund=company;
	}
	/**{@link TestResult#beningCapital}*/
	public BigDecimal getBeningCapital() {
		return beningCapital;
	}
	/**{@link TestResult#beningCapital}*/
	public void setBeningCapital(BigDecimal beningCapital) {
		this.beningCapital = beningCapital;
	}
	
	
}
