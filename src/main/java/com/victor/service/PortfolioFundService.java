package com.victor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.PortfolioFundDAO;
import com.victor.entity.Portfolio;
import com.victor.entity.PortfolioFund;

@Service
public class PortfolioFundService {

	@Autowired
	PortfolioFundDAO portfolioFundDAO;
	
	

	/**
	 * @param id
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findById(java.lang.Long)
	 */
	public PortfolioFund findById(Long id) {
		return portfolioFundDAO.findById(id);
	}



	/**
	 * @param entity
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(com.victor.skandia.entity.BaseEntity)
	 */
	public void saveOrUpdate(PortfolioFund entity) {
		portfolioFundDAO.saveOrUpdate(entity);
	}



	/**
	 * @param entityList
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(java.util.List)
	 */
	public void saveOrUpdate(List<PortfolioFund> entityList) {
		portfolioFundDAO.saveOrUpdate(entityList);
	}



	/** {@link PortfolioFundService#portfolioFundDAO} */
	public void setPortfolioFundDAO(PortfolioFundDAO portfolioFundDAO) {
		this.portfolioFundDAO = portfolioFundDAO;
	}



	/**
	 * @param portfolio
	 * @return
	 * @see com.victor.dao.PortfolioFundDAO#findByPortfolio(com.victor.entity.Portfolio)
	 */
	public List<PortfolioFund> findByPortfolio(Portfolio portfolio) {
		return portfolioFundDAO.findByPortfolio(portfolio);
	}

	
}
