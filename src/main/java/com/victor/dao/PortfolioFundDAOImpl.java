package com.victor.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.victor.entity.Portfolio;
import com.victor.entity.PortfolioFund;

@Repository
public class PortfolioFundDAOImpl extends BaseEntityDAOImpl<PortfolioFund>
		implements PortfolioFundDAO {

	@SuppressWarnings("unchecked")
	public List<PortfolioFund> findByPortfolio(Portfolio portfolio){
		Query query=session.getNamedQuery("portfolioFundByPortfolio");
		query.setLong("portfolioId", portfolio.getId());
		return query.list();
	}
}
