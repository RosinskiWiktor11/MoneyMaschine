package com.victor.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Wiktor portfel posiada relacje do tabel z informacjami (tabela
 *         lacznikowa)
 */
@NamedQueries({
	
})
@Entity
@Table(name = "PORTFOLIO")
public class Portfolio extends BaseEntity {

	// RELATIONS
	@OneToMany(mappedBy = "porfolio", cascade=CascadeType.PERSIST)
	private List<CustomerPortfolio> customerPortfolios;
	@OneToMany(mappedBy = "portfolio", cascade=CascadeType.PERSIST)
	private List<PortfolioFund> portfolioFunds;

	/**
	 * {@link Portfolio}
	 */
	public Portfolio() {
	}

	/** {@link Portfolio#customerPortfolios} */
	public List<CustomerPortfolio> getCustomerPortfolios() {
		if(customerPortfolios==null)
			customerPortfolios=new ArrayList<CustomerPortfolio>();
		return customerPortfolios;
	}

	/** {@link Portfolio#portfolioFunds} */
	public List<PortfolioFund> getPortfolioFunds() {
		return portfolioFunds;
	}

	/** {@link Portfolio#customerPortfolios} */
	public void setCustomerPortfolios(List<CustomerPortfolio> customerPortfolios) {
		this.customerPortfolios = customerPortfolios;
	}

	/** {@link Portfolio#portfolioFunds} */
	public void setPortfolioFunds(List<PortfolioFund> portfolioFunds) {
		this.portfolioFunds = portfolioFunds;
	}

}
