package com.victor.dao;

import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.victor.entity.FundValue;
import com.victor.entity.InvestmentFund;
import com.victor.utils.DateFormat;
import com.victor.utils.DateUtils;

@Repository
public class FundValueDAOImpl extends ValueDAOImpl<FundValue> implements
		FundValueDAO {

	/*
	 * (non-Javadoc) @see
	 * com.victor.skandia.dao.FundValueDAO#isFundValueInDate(java
	 * .util.GregorianCalendar, com.victor.skandia.entity.InvestmentFund)
	 */
	public boolean isFundValueInDate(GregorianCalendar date, InvestmentFund fund) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT EXISTS ( SELECT 1 FROM fund_value where fund_value.investmentfund_id = ");
		buffer.append(fund.getId());
		buffer.append(" and fund_value.date = '");
		buffer.append(DateUtils.convertToStringFormat(date,
				DateFormat.YYYY_MM_DD));
		buffer.append("')");
		Query query = session.createSQLQuery(buffer.toString());
		Boolean isResult = (Boolean) query.uniqueResult();

		return isResult;
	}

	/* (non-Javadoc) @see com.victor.skandia.dao.FundValueDAO#getMaxId() */
	@Override
	public long getMaxId() {
		Query query = session.createSQLQuery("SELECT MAX(id) FROM fund_value");
		BigInteger bigint = (BigInteger) query.uniqueResult();
		return bigint.longValue();

	}

	/*
	 * (non-Javadoc) @see
	 * com.victor.skandia.dao.FundValueDAO#saveOrUpdate(com.victor
	 * .skandia.entity.FundValue)
	 */
	@Override
	public void saveOrUpdate(FundValue fundValue) {
		session.beginTransaction();
		session.save(fundValue);
		session.getTransaction().commit();
	}

	/*
	 * (non-Javadoc) @see
	 * com.victor.skandia.dao.FundValueDAO#findByInvestmentFundId
	 * (java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FundValue> findByInvestmentFundId(Long id) {
		Query query = session
				.createQuery("FROM FundValue where investmentFund.id = ? order by date asc");
		query.setLong(0, id);
		return query.list();
	}

	/*
	 * (non-Javadoc) @see
	 * com.victor.skandia.dao.FundValueDAO#findByInvestmentFund
	 * (com.victor.skandia.entity.InvestmentFund, java.util.GregorianCalendar,
	 * java.util.GregorianCalendar)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FundValue> findByInvestmentFundWithDateLimit(
			InvestmentFund fund, GregorianCalendar startDate,
			GregorianCalendar endDate) {
		Query query = session.getNamedQuery("findFundValueByFundAndDates");
		query.setLong("fundId", fund.getId());
		query.setCalendar("startDate", startDate);
		query.setCalendar("endDate", endDate);
		System.out.println(fund.getId()+"\t"+fund.getShortName());
		System.out.println(DateUtils.convertToStringFormat(startDate, DateFormat.YYYY_MM_DD));
		System.out.println(DateUtils.convertToStringFormat(endDate, DateFormat.YYYY_MM_DD));
//		System.out.println(query.list().size());
		return query.list();
	}

	/*
	 * 
	 * (non-Javadoc) @see
	 * com.victor.skandia.dao.FundValueDAO#findByInvestmentFundId
	 * (com.victor.skandia.entity.InvestmentFund, int)
	 */
	/** zwraca wartosci funduszu od najnowszego do najstarszego */
	@SuppressWarnings("unchecked")
	@Override
	public List<FundValue> findByInvestmentFundIdWithAmountLimit(
			InvestmentFund fund, int numberOfPrices) {
		Query query = session.getNamedQuery("findFundValueByFund");
		query.setLong("fundId", fund.getId());
		query.setMaxResults(numberOfPrices);

		return query.list();
	}

}
