package com.victor.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Wiktor<br/>
 *         fundusz inwestycyjny
 */
@Entity
@Table(name = "INVESTMENT_FUND")
public class InvestmentFund extends FinancialInstrument {

	/** symbol funduszu */
	private String shortName;
	/** pelna nazwa funduszu */
	private String fullName;
	/** dodatkowe informacje jak np. emitent */
	private String description;
	/** poziom ryzyka w skali 0-6 */
	private int risk;
	/** czy obecnie jest aktywny - czy jest na portalu Skandia */
	private boolean active;

	// RELATIONS
	/** historia cen jednostek funduszu */
	@OneToMany(mappedBy = "investmentFund", cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE })
	private List<FundValue> valueHistory;
	/** lista aktywnosci funduszu (okresy obecnosci funduszu na portalu Skandia */
	@OneToMany(mappedBy = "fund")
	private List<FundActivity> fundActivity;
	/** lista portfeli (PortfolioFund.class) w sklad ktorych wchodzil portfel */
	@OneToMany(mappedBy = "investmentFund")
	List<PortfolioFund> portfolioHistory;
	/** waluta bazowa funduszu */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE })
	private Currency currency;
	/** algorytm odpowiedzialny za otwieranie transakcji */
	@ManyToOne
	@JoinColumn(name="opening_algorithm")
	private SkandiaAlgorithm openingAlgorithm;
	/** algorytm odpowiedzialny za zamykanie transakcji */
	@ManyToOne
	@JoinColumn(name="closing_algorithm")
	private SkandiaAlgorithm closingAlgorithm;
	@Embedded
	private SignalWeight weight;

	/**
	 * {@link InvestmentFund}
	 */
	public InvestmentFund() {
		weight= new SignalWeight();
	}

	/** {@link InvestmentFund#active} */
	public boolean isActive() {
		return active;
	}

	/** {@link InvestmentFund#description} */
	public String getDescription() {
		return description;
	}

	/** {@link InvestmentFund#currency} */
	public Currency getCurrency() {
		return currency;
	}

	/** {@link InvestmentFund#fullName} */
	public String getFullName() {
		return fullName;
	}

	/** {@link InvestmentFund#fundActivity} */
	public List<FundActivity> getFundActivity() {
		return fundActivity;
	}

	/** {@link InvestmentFund#portfolioHistory} */
	public List<PortfolioFund> getPortfolioHistory() {
		return portfolioHistory;
	}

	/** {@link InvestmentFund#risk} */
	public int getRisk() {
		return risk;
	}

	/** {@link InvestmentFund#shortName} */
	public String getShortName() {
		return shortName;
	}

	/** {@link InvestmentFund#valueHistory} */
	public List<FundValue> getValueHistory() {
		return valueHistory;
	}

	/** {@link InvestmentFund#active} */
	public void setActive(boolean active) {
		this.active = active;
	}

	/** {@link InvestmentFund#description} */
	public void setDescription(String description) {
		this.description = description;
	}

	/** {@link InvestmentFund#fullName} */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/** {@link InvestmentFund#fundActivity} */
	public void setFundActivity(List<FundActivity> fundActivity) {
		this.fundActivity = fundActivity;
	}

	/** {@link InvestmentFund#portfolioHistory} */
	public void setPortfolioHistory(List<PortfolioFund> portfolioHistory) {
		this.portfolioHistory = portfolioHistory;
	}

	/** {@link InvestmentFund#risk} */
	public void setRisk(int risk) {
		this.risk = risk;
	}

	/** {@link InvestmentFund#shortName} */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/** {@link InvestmentFund#valueHistory} */
	public void setValueHistory(List<FundValue> valueHistory) {
		this.valueHistory = valueHistory;
	}

	/** {@link InvestmentFund#currency} */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public SkandiaAlgorithm getOpeningAlgorithm() {
		return openingAlgorithm;
	}

	public void setOpeningAlgorithm(SkandiaAlgorithm openingAlgorithm) {
		this.openingAlgorithm = openingAlgorithm;
	}

	public SkandiaAlgorithm getClosingAlgorithm() {
		return closingAlgorithm;
	}

	public void setClosingAlgorithm(SkandiaAlgorithm closingAlgorithm) {
		this.closingAlgorithm = closingAlgorithm;
	}

	/**{@link InvestmentFund#weight}*/
	public SignalWeight getWeight() {
		return weight;
	}

	/**{@link InvestmentFund#weight}*/
	public void setWeight(SignalWeight weight) {
		this.weight = weight;
	}

	public String toString(){
		return shortName;
	}
	
}
