package com.victor.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PAYMENT")
public class Payment extends Value {

	// RELATIONS
	@ManyToOne(cascade = { CascadeType.PERSIST })
	private Customer customer;

	public Payment() {
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
