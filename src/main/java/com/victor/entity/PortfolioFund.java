package com.victor.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name = "portfolioFundByPortfolio", query = "FROM PortfolioFund WHERE portfolio.id = :portfolioId")
})
@Entity
@Table(name = "PORTFOLIO_FUND")
public class PortfolioFund extends BaseEntity {
	/** cena jednostki funduszu podczas otwarcia */
	private BigDecimal openingValue;
	/** cena jednostki funduszu podczas zamkniecia*/
	private BigDecimal closingValue;
	/** udzial procentowy funduszu w portfelu */
	private BigDecimal percentage;
	/** liczba zakupionych jednostek */
	private BigDecimal unitAmount;

	// RELATIONS
	@ManyToOne
	private SkandiaAlgorithm openingAlgorithm;
	@ManyToOne
	private SkandiaAlgorithm closingAlgorithm;
	@ManyToOne(cascade = { CascadeType.PERSIST })
	Portfolio portfolio;
	@ManyToOne(cascade = { CascadeType.PERSIST })
	InvestmentFund investmentFund;
	


	/** zraca wartosc funduszu podczas otwarcia - iloczyn ceny za jednostke i ilosc zakupionych jednostek */
	public BigDecimal getOpeningAmount(){
		if(unitAmount!=null && openingValue!=null){
			BigDecimal amount=openingValue.multiply(unitAmount);
			return amount;
		}else{
			return null;
		}
	}
	
	/** zwraca wartosc funduszu podczas zamkniecia - iloczyn ceny za jednostkei i ilosc zakupionych jednostek */
	public BigDecimal getClosingAmount(){
		if(unitAmount!=null && closingValue!=null){
			BigDecimal amount=closingValue.multiply(unitAmount);
			return amount;
		}else{
			return null;
		}
	}
	
	public BigDecimal getOpeningValue() {
		return openingValue;
	}

	public void setOpeningValue(BigDecimal openingValue) {
		this.openingValue = openingValue;
	}

	public BigDecimal getClosingValue() {
		return closingValue;
	}

	public void setClosingValue(BigDecimal closingValue) {
		this.closingValue = closingValue;
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

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	public InvestmentFund getInvestmentFund() {
		return investmentFund;
	}

	public void setInvestmentFund(InvestmentFund investmentFund) {
		this.investmentFund = investmentFund;
	}

	/**{@link PortfolioFund#percentage}*/
	public BigDecimal getPercentage() {
		return percentage;
	}

	/**{@link PortfolioFund#percentage}*/
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	/**{@link PortfolioFund#unitAmount}*/
	public BigDecimal getUnitAmount() {
		return unitAmount;
	}

	/**{@link PortfolioFund#unitAmount}*/
	public void setUnitAmount(BigDecimal unitAmount) {
		this.unitAmount = unitAmount;
	}

}
