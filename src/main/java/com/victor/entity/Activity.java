package com.victor.entity;

import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Wiktor encja aktywnosci m.in. funduszu. Moze byc uniwersalna np. w
 *         stosunku do uzytkownika
 */
@Entity
@Table(name = "ACTIVITY")
public class Activity extends BaseEntity {

	/** data rozpoczecia aktywnosci */
	private GregorianCalendar beginActivityDate;
	/** data zakonczenia aktywnosci */
	private GregorianCalendar endActivityDate;

	/** {@link Activity#beginActivityDate} */
	public GregorianCalendar getBeginActivityDate() {
		return beginActivityDate;
	}

	/** {@link Activity#endActivityDate} */
	public GregorianCalendar getEndActivityDate() {
		return endActivityDate;
	}

	/** {@link Activity#beginActivityDate} */
	public void setBeginActivityDate(GregorianCalendar beginActivityDate) {
		this.beginActivityDate = beginActivityDate;
	}

	/** {@link Activity#endActivityDate} */
	public void setEndActivityDate(GregorianCalendar endActivityDate) {
		this.endActivityDate = endActivityDate;
	}

}
