package com.victor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.PortfolioDAO;
import com.victor.entity.Portfolio;

@Service
public class PortfolioService {

	@Autowired
	PortfolioDAO portfolioDAO;
	
	

	/**
	 * @param id
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findById(java.lang.Long)
	 */
	public Portfolio findById(Long id) {
		return portfolioDAO.findById(id);
	}






	/**
	 * @param entity
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(com.victor.skandia.entity.BaseEntity)
	 */
	public void saveOrUpdate(Portfolio entity) {
		portfolioDAO.saveOrUpdate(entity);
	}



	/**
	 * @param entityList
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(java.util.List)
	 */
	public void saveOrUpdate(List<Portfolio> entityList) {
		portfolioDAO.saveOrUpdate(entityList);
	}



	/** {@link PortfolioService#portfolioDAO} */
	public void setPortfolioDAO(PortfolioDAO portfolioDAO) {
		this.portfolioDAO = portfolioDAO;
	}

}
