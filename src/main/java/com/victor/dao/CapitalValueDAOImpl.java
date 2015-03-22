package com.victor.dao;

import java.util.GregorianCalendar;

import org.hibernate.Query;

import com.victor.entity.CapitalValue;
import com.victor.entity.Customer;
import com.victor.utils.DateFormat;
import com.victor.utils.DateUtils;

public class CapitalValueDAOImpl extends ValueDAOImpl<CapitalValue> implements
		CapitalValueDAO {

	
	public boolean isTodayCapital(Customer customer){
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT EXISTS ( SELECT 1 FROM capital_history where capital_history.customer_id = ");
		buffer.append(customer.getId());
		buffer.append(" and capital_history.date = '");
		buffer.append(DateUtils.convertToStringFormat(new GregorianCalendar(),
				DateFormat.YYYY_MM_DD));
		buffer.append("')");
		Query query = session.createSQLQuery(buffer.toString());
		Boolean result = (Boolean) query.uniqueResult();
		return result;
	}
}
