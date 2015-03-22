package com.victor.dao;



import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.victor.entity.Customer;

@Repository
public class CustomerDAOImpl extends BaseEntityDAOImpl<Customer> implements
		CustomerDAO {

	/* (non-Javadoc) @see com.victor.skandia.dao.CustomerDAO#loginProcess(java.lang.String, java.lang.String) */
	public Customer loginProcess(String login, String passwrod){
		Query query= session.getNamedQuery("loginQuery");
		query.setString("login", login);
		query.setString("password", passwrod);
		Customer customer=(Customer) query.uniqueResult();
		return customer;
	}
	
	/* (non-Javadoc) @see com.victor.skandia.dao.CustomerDAO#findAll() */
	@SuppressWarnings("unchecked")
	public List<Customer> findAll(){
		Query query=session.getNamedQuery("allCustomers");
		 List<Customer>  resultList=query.list();
		 System.out.println(resultList.size());
		 return resultList;
	}
	
	
	/* (non-Javadoc) @see com.victor.skandia.dao.CustomerDAO#findByAgent(com.victor.skandia.entity.Customer) */
	@SuppressWarnings("unchecked")
	public List<Customer> findByAgent(Customer customer){
		Query query=session.getNamedQuery("customersByAgent");
		query.setLong("agentId", customer.getId());
		return query.list();
	}

}
