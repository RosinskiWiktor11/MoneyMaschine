package com.victor.entity;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Value extends BaseEntity {

	/** data wartosci */
	private GregorianCalendar date;
	/** wartosc */
	private BigDecimal value;

	public GregorianCalendar getDate() {
		return date;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
