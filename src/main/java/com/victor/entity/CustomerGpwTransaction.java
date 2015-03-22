package com.victor.entity;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Wiktor Rosinski<br/>
 * Transakcja uzytkownika
 *
 */
@Entity
@Table(name="CUSTOMER_GPW_TRANSACTION")
public class CustomerGpwTransaction extends BaseEntity{

	/** kurs akcji podczas otwarcia transakcji */
	BigDecimal openPrice;
	/** kurs akcji podczas zamkniecia transakcji */
	BigDecimal closePrice;
	/** liczba zakupionych akcji */
	BigDecimal amount;
	/** data otwarcia transakcji */
	private GregorianCalendar openDate;
	/** data zamkniecia transakcji */
	private GregorianCalendar closeDate;
	
	//RELATIONS
	@ManyToOne
	private GpwTransaction transaction;
	@ManyToOne
	private Customer owner;
	@ManyToOne
	private GpwCompany company;
	
	/**{@link CustomerGpwTransaction#openPrice}*/
	public BigDecimal getOpenPrice() {
		return openPrice;
	}
	/**{@link CustomerGpwTransaction#openPrice}*/
	public void setOpenPrice(BigDecimal openPrice) {
		this.openPrice = openPrice;
	}
	/**{@link CustomerGpwTransaction#closePrice}*/
	public BigDecimal getClosePrice() {
		return closePrice;
	}
	/**{@link CustomerGpwTransaction#closePrice}*/
	public void setClosePrice(BigDecimal closePrice) {
		this.closePrice = closePrice;
	}
	/**{@link CustomerGpwTransaction#amount}*/
	public BigDecimal getAmount() {
		return amount;
	}
	/**{@link CustomerGpwTransaction#amount}*/
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**{@link CustomerGpwTransaction#openDate}*/
	public GregorianCalendar getOpenDate() {
		return openDate;
	}
	/**{@link CustomerGpwTransaction#openDate}*/
	public void setOpenDate(GregorianCalendar openDate) {
		this.openDate = openDate;
	}
	/**{@link CustomerGpwTransaction#closeDate}*/
	public GregorianCalendar getCloseDate() {
		return closeDate;
	}
	/**{@link CustomerGpwTransaction#closeDate}*/
	public void setCloseDate(GregorianCalendar closeDate) {
		this.closeDate = closeDate;
	}
	/**{@link CustomerGpwTransaction#transaction}*/
	public GpwTransaction getTransaction() {
		return transaction;
	}
	/**{@link CustomerGpwTransaction#transaction}*/
	public void setTransaction(GpwTransaction transaction) {
		this.transaction = transaction;
	}
	/**{@link CustomerGpwTransaction#owner}*/
	public Customer getOwner() {
		return owner;
	}
	/**{@link CustomerGpwTransaction#owner}*/
	public void setOwner(Customer owner) {
		this.owner = owner;
	}
	/**{@link CustomerGpwTransaction#company}*/
	public GpwCompany getCompany() {
		return company;
	}
	/**{@link CustomerGpwTransaction#company}*/
	public void setCompany(GpwCompany company) {
		this.company = company;
	}
	
	
	
}
