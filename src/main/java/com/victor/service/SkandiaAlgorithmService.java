package com.victor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.SkandiaAlgorithmDAO;
import com.victor.entity.InvestmentFund;
import com.victor.entity.SkandiaAlgorithm;

/**
 * @author Wiktor
 *
 */
@Service
public class SkandiaAlgorithmService {
	
	@Autowired
	private SkandiaAlgorithmDAO skandiaAlgorithmDAO;

	public SkandiaAlgorithm findById(Long id) {
		return skandiaAlgorithmDAO.findById(id);
	}

	public void saveOrUpdate(SkandiaAlgorithm entity) {
		skandiaAlgorithmDAO.saveOrUpdate(entity);
	}

	public void saveOrUpdate(List<SkandiaAlgorithm> entityList) {
		skandiaAlgorithmDAO.saveOrUpdate(entityList);
	}

	public List<SkandiaAlgorithm> findAllByFund(InvestmentFund fund) {
		return skandiaAlgorithmDAO.findAllByFund(fund);
	}

	public SkandiaAlgorithm findActiveOpenAlgorithm(InvestmentFund fund) {
		return skandiaAlgorithmDAO.findActiveOpenAlgorithm(fund);
	}

	public SkandiaAlgorithm findActiveCloseAlgorithm(InvestmentFund fund) {
		return skandiaAlgorithmDAO.findActiveCloseAlgorithm(fund);
	}

	
	
	
	
}
