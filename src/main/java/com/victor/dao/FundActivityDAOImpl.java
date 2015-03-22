package com.victor.dao;

import java.util.GregorianCalendar;

import org.springframework.stereotype.Repository;

import com.victor.entity.FundActivity;
import com.victor.entity.InvestmentFund;

/**
 * @author Wiktor
 *
 */
@Repository
public class FundActivityDAOImpl extends BaseEntityDAOImpl<FundActivity>
		implements FundActivityDAO {

	/*
	 * (non-Javadoc) @see
	 * com.victor.skandia.dao.FundActivityDAO#findByFundAndDate
	 * (com.victor.skandia.entity.InvestmentFund, java.util.GregorianCalendar)
	 */
	public FundActivity[] findByFundAndDate(InvestmentFund investmentFund,
			GregorianCalendar date) {
		return null;
	}

}
