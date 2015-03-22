package com.victor.service;

import java.util.List;

import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.GpwAlgorithmDAO;
import com.victor.entity.GpwAlgorithm;
import com.victor.entity.GpwCompany;

/**
 * @author Wiktor Rosinski
 *
 */
@Service
public class GpwAlgorithmService{
	
	@Autowired
	private GpwAlgorithmDAO gpwAlgorithmDAO;

	/**
	 * @param id
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findById(java.lang.Long)
	 */
	public GpwAlgorithm findById(Long id) {
		return gpwAlgorithmDAO.findById(id);
	}

	/**
	 * @param entity
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(com.victor.entity.BaseEntity)
	 */
	public void saveOrUpdate(GpwAlgorithm entity) {
		gpwAlgorithmDAO.saveOrUpdate(entity);
	}

	/**
	 * @param entityList
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(java.util.List)
	 */
	public void saveOrUpdate(List<GpwAlgorithm> entityList) {
		gpwAlgorithmDAO.saveOrUpdate(entityList);
	}

	/**
	 * @param company
	 * @return
	 * @see com.victor.dao.GpwAlgorithmDAO#findAllByCompany(com.victor.entity.GpwCompany)
	 */
	public List<GpwAlgorithm> findAllByCompany(GpwCompany company) {
		return gpwAlgorithmDAO.findAllByCompany(company);
	}

	/**
	 * @param company
	 * @return
	 * @see com.victor.dao.GpwAlgorithmDAO#findActiveOpenAlgorithm(com.victor.entity.GpwCompany)
	 */
	public GpwAlgorithm findActiveOpenAlgorithm(GpwCompany company) {
		return gpwAlgorithmDAO.findActiveOpenAlgorithm(company);
	}

	/**
	 * @param company
	 * @return
	 * @see com.victor.dao.GpwAlgorithmDAO#findActiveCloseAlgorithm(com.victor.entity.GpwCompany)
	 */
	public GpwAlgorithm findActiveCloseAlgorithm(GpwCompany company) {
		return gpwAlgorithmDAO.findActiveCloseAlgorithm(company);
	}
	
}
