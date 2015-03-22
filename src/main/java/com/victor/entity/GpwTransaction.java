package com.victor.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Wiktor Rosinski<br/>
 * 	transakcja na rynku GPW
 *
 */
@Entity
@Table(name="GPW_TRANSACTION")
public class GpwTransaction extends BaseEntity{
	
	@ManyToOne
	private Customer author;
	@ManyToOne
	@JoinColumn(name="opening_algorithm")
	private GpwAlgorithm openingAlgorithm;
	@ManyToOne
	@JoinColumn(name="closing_algorithm")
	private GpwAlgorithm closingAlgorithm;
	
	
	private transient List<CustomerGpwTransaction> customerTransactionList;

	public void addCustomerTransaction(CustomerGpwTransaction customerTransaction){
		if(customerTransactionList==null)
			customerTransactionList=new ArrayList<CustomerGpwTransaction>();
		customerTransactionList.add(customerTransaction);
	}
	
	/**{@link GpwTransaction#author}*/
	public Customer getAuthor() {
		return author;
	}

	/**{@link GpwTransaction#author}*/
	public void setAuthor(Customer author) {
		this.author = author;
	}

	/**{@link GpwTransaction#customerTransactionList}*/
	public List<CustomerGpwTransaction> getCustomerTransactionList() {
		return customerTransactionList;
	}

	/**{@link GpwTransaction#customerTransactionList}*/
	public void setCustomerTransactionList(
			List<CustomerGpwTransaction> customerTransactionList) {
		this.customerTransactionList = customerTransactionList;
	}

	/**{@link GpwTransaction#openingAlgorithm}*/
	public GpwAlgorithm getOpeningAlgorithm() {
		return openingAlgorithm;
	}

	/**{@link GpwTransaction#openingAlgorithm}*/
	public void setOpeningAlgorithm(GpwAlgorithm openingAlgorithm) {
		this.openingAlgorithm = openingAlgorithm;
	}

	/**{@link GpwTransaction#closingAlgorithm}*/
	public GpwAlgorithm getClosingAlgorithm() {
		return closingAlgorithm;
	}

	/**{@link GpwTransaction#closingAlgorithm}*/
	public void setClosingAlgorithm(GpwAlgorithm closingAlgorithm) {
		this.closingAlgorithm = closingAlgorithm;
	}
	

}
