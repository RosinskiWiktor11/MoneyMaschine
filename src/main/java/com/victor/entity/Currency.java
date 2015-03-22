package com.victor.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Wiktor slownik walut
 */
@Entity
@Table(name = "CURRENCY")
public class Currency extends BaseEntity {

	/** symbol waluty */
	private String name;

	/** lista funduszy posiadajacych dana encje waluty bazowej */
	@OneToMany(mappedBy = "currency", cascade = { CascadeType.PERSIST})
	private List<InvestmentFund> investmentFunds;

	/** {@link Currency#name} */
	public void setName(String name) {
		this.name = name;
	}

	/** {@link Currency#name} */
	public String getName() {
		return name;
	}

	/** {@link Currency#investmentFunds} */
	public void setInvestmentFunds(List<InvestmentFund> investmentFunds) {
		this.investmentFunds = investmentFunds;
	}

	/** {@link Currency#investmentFunds} */
	public List<InvestmentFund> getInvestmentFunds() {
		return investmentFunds;
	}

}
