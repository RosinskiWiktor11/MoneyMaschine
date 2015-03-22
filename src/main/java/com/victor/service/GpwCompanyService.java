package com.victor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.GpwCompanyDAO;
import com.victor.entity.Customer;
import com.victor.entity.GpwCompany;

/**
 * @author Wiktor Rosinski
 *
 */
@Service
public class GpwCompanyService {
	@Autowired
	private GpwCompanyDAO gpwCompanyDAO;

	/**
	 * @param id
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findById(java.lang.Long)
	 */
	public GpwCompany findById(Long id) {
		return gpwCompanyDAO.findById(id);
	}

	/**
	 * @param entity
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(com.victor.entity.BaseEntity)
	 */
	public void saveOrUpdate(GpwCompany entity) {
		gpwCompanyDAO.saveOrUpdate(entity);
	}

	/**
	 * @return
	 * @see com.victor.dao.GpwCompanyDAO#findAllActive()
	 */
	public List<GpwCompany> findAllActive() {
		return gpwCompanyDAO.findAllActive();
	}

	/**
	 * @param entityList
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(java.util.List)
	 */
	public void saveOrUpdate(List<GpwCompany> entityList) {
		gpwCompanyDAO.saveOrUpdate(entityList);
	}

	/**
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findAll()
	 */
	public List<GpwCompany> findAll() {
		return gpwCompanyDAO.findAll();
	}

	/**
	 * @param customer
	 * @return
	 * @see com.victor.dao.GpwCompanyDAO#findBounghtCompanies(com.victor.entity.Customer)
	 */
	public List<GpwCompany> findBounghtCompanies(Customer customer) {
		return gpwCompanyDAO.findBounghtCompanies(customer);
	}
	
	
}
