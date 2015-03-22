package com.victor.dao;

import java.util.GregorianCalendar;
import java.util.List;

import com.victor.entity.FundValue;
import com.victor.entity.InvestmentFund;

public interface FundValueDAO extends ValueDAO<FundValue> {

	/** sprawdza czy dla danej daty jest juz wartosc funduszu w bazie danych */
	public boolean isFundValueInDate(GregorianCalendar date, InvestmentFund fund);

	public long getMaxId();

	public void saveOrUpdate(FundValue fundValue);

	public List<FundValue> findByInvestmentFundId(Long id);

	public List<FundValue> findByInvestmentFundWithDateLimit(
			InvestmentFund fund, GregorianCalendar startDate,
			GregorianCalendar endDate);

	public List<FundValue> findByInvestmentFundIdWithAmountLimit(
			InvestmentFund fund, int numberOfPrices);
}
