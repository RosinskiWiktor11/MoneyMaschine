package com.victor.dao;

import com.victor.entity.Currency;

/**
 * @author Wiktor
 *
 */
public interface CurrencyDAO extends BaseEntityDAO<Currency> {

	/** szuka waluty po nazwie */
	public Currency findByName(String name);

	public void saveOrUpdate(Currency currency);

}
