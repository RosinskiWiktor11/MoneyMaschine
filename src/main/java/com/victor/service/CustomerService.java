package com.victor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.CustomerDAO;
import com.victor.entity.Customer;

@Service
public class CustomerService {

	@Autowired
	CustomerDAO customerDAO;

	/** {@link CustomerService#customerDAO} */
	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	/**
	 * @param login
	 * @param passwrod
	 * @return
	 * @see com.victor.dao.CustomerDAO#loginProcess(java.lang.String, java.lang.String)
	 */
	public Customer loginProcess(String login, String passwrod) {
		return customerDAO.loginProcess(login, passwrod);
	}

	/**
	 * @param id
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findById(java.lang.Long)
	 */
	public Customer findById(Long id) {
		return customerDAO.findById(id);
	}

	/**
	 * @param entity
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(com.victor.skandia.entity.BaseEntity)
	 */
	public void saveOrUpdate(Customer entity) {
		customerDAO.saveOrUpdate(entity);
	}

	/**
	 * @param entityList
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(java.util.List)
	 */
	public void saveOrUpdate(List<Customer> entityList) {
		customerDAO.saveOrUpdate(entityList);
	}

	/**
	 * @return
	 * @see com.victor.dao.CustomerDAO#findAll()
	 */
	public List<Customer> findAll() {
		return customerDAO.findAll();
	}

	/**
	 * @param customer
	 * @return
	 * @see com.victor.dao.CustomerDAO#findByAgent(com.victor.entity.Customer)
	 */
	public List<Customer> findByAgent(Customer customer) {
		return customerDAO.findByAgent(customer);
	}
	
	

}
