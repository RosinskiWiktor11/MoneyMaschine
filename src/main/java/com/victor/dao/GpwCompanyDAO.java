package com.victor.dao;

import java.util.List;

import com.victor.entity.Customer;
import com.victor.entity.GpwCompany;

/**
 * @author Wiktor Rosinski
 *
 */
public interface GpwCompanyDAO extends BaseEntityDAO<GpwCompany>{

	/** wyszukuje wszystkie aktywne obecnie na gieldzie spolki */
	public List<GpwCompany> findAllActive();
	
	/** wyszukuje kupione przez uzytkownika spolki, ktore nie sa jeszcze sprzedane */
	public List<GpwCompany> findBounghtCompanies(Customer customer);
}
