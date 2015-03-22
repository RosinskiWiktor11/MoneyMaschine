package com.victor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.CustomerGpwTransactionDAO;
import com.victor.entity.CustomerGpwTransaction;

/**
 * @author Wiktor Rosinski
 *
 */
@Service
public class CustomerGpwTransactionService {

	@Autowired
	private CustomerGpwTransactionDAO customerGpwTransactionDAO;

	/**
	 * @param id
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findById(java.lang.Long)
	 */
	public CustomerGpwTransaction findById(Long id) {
		return customerGpwTransactionDAO.findById(id);
	}

	/**
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findAll()
	 */
	public List<CustomerGpwTransaction> findAll() {
		return customerGpwTransactionDAO.findAll();
	}

	/**
	 * @param entity
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(com.victor.entity.BaseEntity)
	 */
	public void saveOrUpdate(CustomerGpwTransaction entity) {
		customerGpwTransactionDAO.saveOrUpdate(entity);
	}

	/**
	 * @param entityList
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(java.util.List)
	 */
	public void saveOrUpdate(List<CustomerGpwTransaction> entityList) {
		customerGpwTransactionDAO.saveOrUpdate(entityList);
	}
	
	
}
