package com.victor.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.victor.entity.InvestmentFund;

@Repository
public class InvestmentFundDAOImpl extends BaseEntityDAOImpl<InvestmentFund>
		implements InvestmentFundDAO {

	/*
	 * (non-Javadoc) @see
	 * com.victor.skandia.dao.InvestmentFundDAO#findAllActive()
	 */
	public List<InvestmentFund> findAllActive() {
		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append("from ").append(persistentClass.getName());
		queryBuffer.append(" where active = ? order by shortname");
		Query query = session.createQuery(queryBuffer.toString());
		query.setBoolean(0, true);
		@SuppressWarnings("unchecked")
		List<InvestmentFund> results = query.list();
		return results;
	}

	/*
	 * (non-Javadoc) @see
	 * com.victor.skandia.dao.InvestmentFundDAO#saveOrUpdate(java.util.List)
	 */
	public void saveOrUpdate(List<InvestmentFund> fundList) {
		session.beginTransaction();
		for (InvestmentFund fund : fundList) {
			session.persist(fund);
		}
		session.getTransaction().commit();
	}

}
