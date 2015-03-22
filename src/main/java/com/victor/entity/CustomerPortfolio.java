package com.victor.entity;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//***************************************
//** Historia portfeli uzytkownika     **
//** jak rowniez indywidualny portfel  **
//** dla kazdego uzytkownika           **
//***************************************
@Entity
@Table(name = "CUSTOMER_PORTFOLIO")
public class CustomerPortfolio extends BaseEntity {

	/** data otwarcia portfela */
	private GregorianCalendar openingDate;
	/** data zamkniecia portfela */
	private GregorianCalendar closingDate;
	/** wartosc portfela podczas otwarcia */
	private BigDecimal openingValue;
	/** wartosc portfela podczas zamkniecia */
	private BigDecimal closingValue;

	// RELATIONS
	@ManyToOne(cascade = { CascadeType.PERSIST })
	Customer customer;
	@ManyToOne(cascade = { CascadeType.PERSIST })
	Portfolio porfolio;
	
	public void addPortfolio(Portfolio portfolio){
		this.porfolio=portfolio;
		portfolio.getCustomerPortfolios().add(this);
	}
	
	/** ustamia klienta do portfela i u klienta ustawia ten portfel jako jego aktualny i dodaje do jego historii portfeli */
	public void addCustomer(Customer customer){
		this.customer=customer;
		customer.setPortfolio(this);
		customer.getPortfolioHistory().add(this);
	}

	public GregorianCalendar getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(GregorianCalendar openingDate) {
		this.openingDate = openingDate;
	}

	public GregorianCalendar getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(GregorianCalendar closingDate) {
		this.closingDate = closingDate;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Portfolio getPorfolio() {
		return porfolio;
	}

	public void setPorfolio(Portfolio porfolio) {
		this.porfolio = porfolio;
	}
}
