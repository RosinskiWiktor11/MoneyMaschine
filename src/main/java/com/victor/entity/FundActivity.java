package com.victor.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Wiktor<br/>
 *         Aktywnosc funduszu. Przedstawia okresy obecnosci funduszu na portalu
 *         Sknadia
 *
 */
@Entity
@Table(name = "FUND_ACTICITY")
public class FundActivity extends BaseEntity {

	// RELATIONS
	/** Fundusz z ktorym jest powiazana aktywnosc */
	@ManyToOne(cascade = { CascadeType.PERSIST })
	private InvestmentFund fund;
	/**
	 * aktywnosc przedstawiajaca okres rozpoczecia i zakonczenia aktywnosci
	 * funduszu
	 */
	@ManyToOne
	private Activity activity;

	/**
	 * {@link FundActivity}
	 */
	public FundActivity() {
	}

	/** {@link FundActivity#activity} */
	public Activity getActivity() {
		return activity;
	}

	/** {@link FundActivity#fund} */
	public InvestmentFund getFund() {
		return fund;
	}

	/** {@link FundActivity#activity} */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	/** {@link FundActivity#fund} */
	public void setFund(InvestmentFund fund) {
		this.fund = fund;
	}

}
