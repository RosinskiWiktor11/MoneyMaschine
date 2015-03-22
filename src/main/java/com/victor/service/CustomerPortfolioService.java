package com.victor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.CustomerPortfolioDAO;
import com.victor.entity.CustomerPortfolio;

@Service
public class CustomerPortfolioService {

	@Autowired
	CustomerPortfolioDAO customerPorfolioDAO;
	
	

	/**
	 * @param id
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findById(java.lang.Long)
	 */
	public CustomerPortfolio findById(Long id) {
		return customerPorfolioDAO.findById(id);
	}



	/**
	 * @param entity
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(com.victor.skandia.entity.BaseEntity)
	 */
	public void saveOrUpdate(CustomerPortfolio entity) {
		customerPorfolioDAO.saveOrUpdate(entity);
	}



	/**
	 * @param entityList
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(java.util.List)
	 */
	public void saveOrUpdate(List<CustomerPortfolio> entityList) {
		customerPorfolioDAO.saveOrUpdate(entityList);
	}



	/** {@link CustomerPortfolioService#customerPorfolioDAO} */
	public void setCustomerPorfolioDAO(CustomerPortfolioDAO customerPorfolioDAO) {
		this.customerPorfolioDAO = customerPorfolioDAO;
	}

}
