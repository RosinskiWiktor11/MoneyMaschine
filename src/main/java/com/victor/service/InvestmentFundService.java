package com.victor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.InvestmentFundDAO;
import com.victor.entity.InvestmentFund;

@Service
public class InvestmentFundService {

	@Autowired
	InvestmentFundDAO investmentFundDAO;

	/** {@link InvestmentFundService#investmentFundDAO} */
	public void setInvestmentFundDAO(InvestmentFundDAO investmentFundDAO) {
		this.investmentFundDAO = investmentFundDAO;
	}

	public InvestmentFund findById(Long id) {
		return investmentFundDAO.findById(id);
	}

	/** {@link InvestmentFundDAO#findAllActive} */
	public List<InvestmentFund> findAllActive() {
		return investmentFundDAO.findAllActive();
	}

	/** {@link InvestmentFundDAO#saveOrUpdate} */
	public void saveOrUpdate(List<InvestmentFund> fundList) {
		investmentFundDAO.saveOrUpdate(fundList);
	}

	public void saveOrUpdate(InvestmentFund entity) {
		investmentFundDAO.saveOrUpdate(entity);
	}

}
