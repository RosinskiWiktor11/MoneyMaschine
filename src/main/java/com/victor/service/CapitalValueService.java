package com.victor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.CapitalValueDAO;
import com.victor.entity.CapitalValue;
import com.victor.entity.Customer;

@Service
public class CapitalValueService {

	@Autowired
	CapitalValueDAO capitalValueDAO;

	
	
	/**
	 * @param id
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findById(java.lang.Long)
	 */
	public CapitalValue findById(Long id) {
		return capitalValueDAO.findById(id);
	}



	/**
	 * @param entity
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(com.victor.skandia.entity.BaseEntity)
	 */
	public void saveOrUpdate(CapitalValue entity) {
		capitalValueDAO.saveOrUpdate(entity);
	}



	/**
	 * @param entityList
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(java.util.List)
	 */
	public void saveOrUpdate(List<CapitalValue> entityList) {
		capitalValueDAO.saveOrUpdate(entityList);
	}



	/**
	 * @param customer
	 * @return
	 * @see com.victor.dao.CapitalValueDAO#isTodayCapital(com.victor.entity.Customer)
	 */
	public boolean isTodayCapital(Customer customer) {
		return capitalValueDAO.isTodayCapital(customer);
	}



	/** {@link CapitalValueService#capitalValueDAO} */
	public void setCapitalValueDAO(CapitalValueDAO capitalValueDAO) {
		this.capitalValueDAO = capitalValueDAO;
	}

}
