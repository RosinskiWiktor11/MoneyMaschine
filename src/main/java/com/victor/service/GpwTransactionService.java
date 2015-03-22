package com.victor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.GpwTransactionDAO;
import com.victor.entity.GpwTransaction;

/**
 * @author Wiktor Rosinski
 *
 */
@Service
public class GpwTransactionService {

	@Autowired
	private GpwTransactionDAO gpwTransactionDAO;

	/**
	 * @param id
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findById(java.lang.Long)
	 */
	public GpwTransaction findById(Long id) {
		return gpwTransactionDAO.findById(id);
	}

	/**
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findAll()
	 */
	public List<GpwTransaction> findAll() {
		return gpwTransactionDAO.findAll();
	}

	/**
	 * @param entity
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(com.victor.entity.BaseEntity)
	 */
	public void saveOrUpdate(GpwTransaction entity) {
		gpwTransactionDAO.saveOrUpdate(entity);
	}

	/**
	 * @param entityList
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(java.util.List)
	 */
	public void saveOrUpdate(List<GpwTransaction> entityList) {
		gpwTransactionDAO.saveOrUpdate(entityList);
	}
	
	
	
}
