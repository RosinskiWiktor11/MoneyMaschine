package com.victor.dao;

import java.util.List;

import com.victor.entity.InvestmentFund;
import com.victor.entity.SkandiaAlgorithm;

/**
 * @author Wiktor
 *
 */
public interface SkandiaAlgorithmDAO extends BaseEntityDAO<SkandiaAlgorithm> {
	
	public List<SkandiaAlgorithm> findAllByFund(InvestmentFund fund);
	public SkandiaAlgorithm findActiveOpenAlgorithm(InvestmentFund fund);
	public SkandiaAlgorithm findActiveCloseAlgorithm(InvestmentFund fund);

}
