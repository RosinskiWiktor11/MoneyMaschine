package com.victor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.CurrencyDAO;
import com.victor.entity.Currency;

/**
 * @author Wiktor
 *
 */
@Service
public class CurrencyService {

	@Autowired
	CurrencyDAO currencyDAO;

	/** {@link CurrencyService#currencyDAO} */
	public void setCurrencyDAO(CurrencyDAO currencyDAO) {
		this.currencyDAO = currencyDAO;
	}

	public Currency findByName(String name) {
		return currencyDAO.findByName(name);
	}

	public Currency findById(Long id) {
		return currencyDAO.findById(id);
	}

	public void saveOrUpdate(Currency currency) {
		currencyDAO.saveOrUpdate(currency);
	}

}
