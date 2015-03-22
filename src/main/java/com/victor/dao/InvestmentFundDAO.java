package com.victor.dao;

import java.util.List;

import com.victor.entity.InvestmentFund;

public interface InvestmentFundDAO extends BaseEntityDAO<InvestmentFund> {

	/**
	 * pobiera wszystkie aktywne fundusze - obecne na portalu Skandia w dniu
	 * importu
	 */
	public List<InvestmentFund> findAllActive();

	/** zapisuje cala liste funduszy na jednej transakcji */
	public void saveOrUpdate(List<InvestmentFund> fundList);

}
