package com.victor.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.victor.entity.Customer;
import com.victor.entity.GpwCompany;

/**
 * @author Wiktor Rosinski <br/>
 * 
 *
 */
@Repository
public class GpwCompanyDAOImpl extends BaseEntityDAOImpl<GpwCompany> implements GpwCompanyDAO{

	/* (non-Javadoc) @see com.victor.dao.GpwCompanyDAO#findAllActive() */
	@SuppressWarnings("unchecked")
	@Override
	public List<GpwCompany> findAllActive() {
		Query query =session.getNamedQuery("findAllActiveGpwCompany");
		return query.list();
	}

	/* (non-Javadoc) @see com.victor.dao.GpwCompanyDAO#findBounghtCompanies(com.victor.entity.Customer) */
	@SuppressWarnings("unchecked")
	@Override
	public List<GpwCompany> findBounghtCompanies(Customer customer) {
		Query query=session.getNamedQuery("findBoughtCompanyByCustomer");
		query.setLong("customerId", customer.getId());
		return query.list();
	}
}
