package com.victor.dao;

import java.util.GregorianCalendar;

import com.victor.entity.FundActivity;
import com.victor.entity.InvestmentFund;

/**
 * @author Wiktor
 *
 */
public interface FundActivityDAO extends BaseEntityDAO<FundActivity> {

	public FundActivity[] findByFundAndDate(InvestmentFund investmentFund,
			GregorianCalendar date);
}
