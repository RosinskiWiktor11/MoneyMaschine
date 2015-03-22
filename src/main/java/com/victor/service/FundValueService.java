package com.victor.service;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.FundValueDAO;
import com.victor.entity.FundValue;
import com.victor.entity.InvestmentFund;

@Service
public class FundValueService {

	@Autowired
	FundValueDAO fundValueDAO;

	/** {@link FundValueService#fundValueDAO} */
	public void setFundValueDAO(FundValueDAO fundValueDAO) {
		this.fundValueDAO = fundValueDAO;
	}

	public FundValue findById(Long id) {
		return fundValueDAO.findById(id);
	}

	public boolean isFundValueInDate(GregorianCalendar date, InvestmentFund fund) {
		return fundValueDAO.isFundValueInDate(date, fund);
	}

	public long getMaxId() {
		return fundValueDAO.getMaxId();
	}

	public FundValueDAO getFundValueDAO() {
		return fundValueDAO;
	}

	public void saveOrUpdate(FundValue fundValue) {
		fundValueDAO.saveOrUpdate(fundValue);
	}

	public void saveOrUpdate(List<FundValue> entityList) {
		fundValueDAO.saveOrUpdate(entityList);
	}

	public List<FundValue> findByInvestmentFundId(Long id) {
		return fundValueDAO.findByInvestmentFundId(id);
	}

	public List<FundValue> findByInvestmentFundWithDateLimit(
			InvestmentFund fund, GregorianCalendar startDate,
			GregorianCalendar endDate) {
		return fundValueDAO.findByInvestmentFundWithDateLimit(fund, startDate,
				endDate);
	}

	public List<FundValue> findByInvestmentFundIdWithAmountLimit(
			InvestmentFund fund, int numberOfPrices) {
		return fundValueDAO.findByInvestmentFundIdWithAmountLimit(fund,
				numberOfPrices);
	}

}
