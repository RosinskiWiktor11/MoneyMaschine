package com.victor.dao;

import com.victor.entity.CapitalValue;
import com.victor.entity.Customer;

public interface CapitalValueDAO extends ValueDAO<CapitalValue> {

	public boolean isTodayCapital(Customer customer);
}
