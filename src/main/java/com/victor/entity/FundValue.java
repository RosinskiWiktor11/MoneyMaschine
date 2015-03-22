package com.victor.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
		@NamedQuery(name = "findFundValueByFundAndDates", query = "FROM FundValue WHERE investmentFund.id = :fundId AND date >= :startDate AND  date <= :endDate ORDER BY date ASC "),
		@NamedQuery(name = "findFundValueByFund", query = "FROM FundValue WHERE investmentFund.id = :fundId  ORDER BY date DESC ") })
@Entity
@Table(name = "FUND_VALUE")
public class FundValue extends Value {

	@ManyToOne(cascade = { CascadeType.PERSIST })
	private InvestmentFund investmentFund;

	public InvestmentFund getInvestmentFund() {
		return investmentFund;
	}

	public void setInvestmentFund(InvestmentFund investmentFund) {
		this.investmentFund = investmentFund;
	}

}
