package com.victor.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.victor.entity.Currency;

/**
 * @author Wiktor
 *
 */
@Repository
public class CurrencyDAOImpl extends BaseEntityDAOImpl<Currency> implements
		CurrencyDAO {

	@SuppressWarnings("unchecked")
	public Currency findByName(String name) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from ").append(persistentClass.getName());
		buffer.append(" where name = ?");
		Query query = session.createQuery(buffer.toString());
		query.setString(0, name);
		List<Currency> currencyList = query.list();
		if (currencyList.size() > 0) {
			return currencyList.get(0);
		} else {
			return null;
		}
	}

	public void saveOrUpdate(Currency currency) {
		session.beginTransaction();
		session.persist(currency);
		session.getTransaction().commit();
	}

}
