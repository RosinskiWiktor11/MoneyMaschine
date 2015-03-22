package com.victor.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.victor.entity.InvestmentFund;
import com.victor.entity.SkandiaAlgorithm;

/**
 * @author Wiktor
 *
 */
@Repository
public class SkandiaAlgorithmDAOImpl extends BaseEntityDAOImpl<SkandiaAlgorithm> implements SkandiaAlgorithmDAO{

	/* (non-Javadoc) @see com.victor.skandia.dao.SkandiaAlgorithmDAO#findAllByFund(com.victor.skandia.entity.InvestmentFund) */
	@SuppressWarnings("unchecked")
	@Override
	public List<SkandiaAlgorithm> findAllByFund(InvestmentFund fund) {
		Query query=session.getNamedQuery("findAllByFund");
		query.setLong("fundId", fund.getId());
		return query.list();
	}
	public SkandiaAlgorithm findActiveOpenAlgorithm(InvestmentFund fund){
		Query query=session.getNamedQuery("findOpenActiveByFund");
		query.setLong("fundId", fund.getId());
		return (SkandiaAlgorithm) query.uniqueResult();
	}

	public SkandiaAlgorithm findActiveCloseAlgorithm(InvestmentFund fund){
		Query query=session.getNamedQuery("findCloseActiveByFund");
		query.setLong("fundId", fund.getId());
		return (SkandiaAlgorithm) query.uniqueResult();
	}
}
