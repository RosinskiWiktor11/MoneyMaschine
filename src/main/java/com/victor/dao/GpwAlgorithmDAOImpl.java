package com.victor.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.victor.entity.GpwAlgorithm;
import com.victor.entity.GpwCompany;

/**
 * @author Wiktor Rosinski
 *
 */
@Repository
public class GpwAlgorithmDAOImpl extends BaseEntityDAOImpl<GpwAlgorithm> implements GpwAlgorithmDAO{

	/* (non-Javadoc) @see com.victor.dao.GpwAlgorithmDAO#findAllByCompany(com.victor.entity.GpwCompany) */
	@SuppressWarnings("unchecked")
	@Override
	public List<GpwAlgorithm> findAllByCompany(GpwCompany company) {
		Query query =session.getNamedQuery("gpwAlgorithmAllByCompany");
		query.setLong("companyId", company.getId());
		List<GpwAlgorithm> result= query.list();
		if(result==null)
			result=new ArrayList<GpwAlgorithm>();
		return result;
	}

	/* (non-Javadoc) @see com.victor.dao.GpwAlgorithmDAO#findActiveOpenAlgorithm(com.victor.entity.GpwCompany) */
	@Override
	public GpwAlgorithm findActiveOpenAlgorithm(GpwCompany company) {
		Query query=session.getNamedQuery("findGpwAlgorithmOpenActiveByFund");
		query.setLong("companyId", company.getId());
		return (GpwAlgorithm) query.uniqueResult();
	}

	/* (non-Javadoc) @see com.victor.dao.GpwAlgorithmDAO#findActiveCloseAlgorithm(com.victor.entity.GpwCompany) */
	@Override
	public GpwAlgorithm findActiveCloseAlgorithm(GpwCompany company) {
		Query query=session.getNamedQuery("findGpwAlgorithmCloseActiveByFund");
		query.setLong("companyId", company.getId());
		return (GpwAlgorithm) query.uniqueResult();
	}

}
