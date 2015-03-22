package com.victor.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
@NamedQueries({
	@NamedQuery(name="loginQuery", query="from Customer where login = :login and password= :password"),
	@NamedQuery(name="allCustomers", query="from Customer"),
	@NamedQuery(name="customersByAgent", query="from Customer where agent.id = :agentId")
})
public class Customer extends BaseEntity {

	private String login;
	private String password;
	private BigDecimal capital;
	private String skandiaLogin;
	private String skandiaPassword;

	// RELATIONS
	@ManyToOne
	private CustomerPortfolio portfolio;
	@OneToMany(mappedBy = "customer")
	private List<CustomerPortfolio> portfolioHistory;
	@OneToMany(mappedBy = "customer")
	private List<CapitalValue> capitalHistory;
	@OneToMany(mappedBy = "customer")
	private List<Payment> paymentHistory;
	/** przedstawia kto jest przedstawicielem tego klienta */
	@ManyToOne
	private Customer agent;
	
	public Customer() {
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getCapital() {
		return capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}



	/**{@link Customer#portfolio}*/
	public CustomerPortfolio getPortfolio() {
		return portfolio;
	}

	/**{@link Customer#portfolio}*/
	public void setPortfolio(CustomerPortfolio portfolio) {
		this.portfolio = portfolio;
	}

	public List<CustomerPortfolio> getPortfolioHistory() {
		return portfolioHistory;
	}

	public void setPortfolioHistory(List<CustomerPortfolio> portfolioHistory) {
		this.portfolioHistory = portfolioHistory;
	}

	public List<Payment> getPaymentHistory() {
		return paymentHistory;
	}

	public void setPaymentHistory(List<Payment> paymentHistory) {
		this.paymentHistory = paymentHistory;
	}

	public List<CapitalValue> getCapitalHistory() {
		return capitalHistory;
	}

	public void setCapitalHistory(List<CapitalValue> capitalHistory) {
		this.capitalHistory = capitalHistory;
	}

	/**{@link Customer#agent}*/
	public Customer getAgent() {
		return agent;
	}

	/**{@link Customer#agent}*/
	public void setAgent(Customer agent) {
		this.agent = agent;
	}

	/**{@link Customer#skandiaLogin}*/
	public String getSkandiaLogin() {
		return skandiaLogin;
	}

	/**{@link Customer#skandiaLogin}*/
	public void setSkandiaLogin(String skandiaLogin) {
		this.skandiaLogin = skandiaLogin;
	}

	/**{@link Customer#skandiaPassword}*/
	public String getSkandiaPassword() {
		return skandiaPassword;
	}

	/**{@link Customer#skandiaPassword}*/
	public void setSkandiaPassword(String skandiaPassword) {
		this.skandiaPassword = skandiaPassword;
	}

}
