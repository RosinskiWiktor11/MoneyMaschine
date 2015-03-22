package com.victor.dao;

import java.util.List;

import com.victor.entity.Portfolio;
import com.victor.entity.PortfolioFund;

public interface PortfolioFundDAO extends BaseEntityDAO<PortfolioFund> {

	/** wyszukuje PorfolioFund wg portfela */
	public List<PortfolioFund> findByPortfolio(Portfolio portfolio);
}
