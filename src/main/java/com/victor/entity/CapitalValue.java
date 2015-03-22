package com.victor.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

@NamedQueries({
	
})
@Entity
@Table(name = "CAPITAL_HISTORY")
public class CapitalValue extends Value {

	@ManyToOne(cascade = { CascadeType.PERSIST })
	Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
