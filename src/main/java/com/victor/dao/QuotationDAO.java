package com.victor.dao;

import java.util.GregorianCalendar;
import java.util.List;

import com.victor.entity.GpwCompany;
import com.victor.entity.Quotation;
import com.victor.utils.DateFormat;

/**
 * @author Wiktor Rosinski
 *
 */
public interface QuotationDAO extends BaseEntityDAO<Quotation>{

	/** true, gdy znalezione zostaly notowania dla wskazanej spolki w danym dniu<br/>
	 * false, gdy nie zostaly znalezione notowania */
	public boolean isQuotationForCompanyInDate(GpwCompany company, String stringDate, DateFormat dateFormat);
	
	/** wyszukuje liste notowan w zadanych zakresie dat posortowane od najstarszej do najmlodszej*/
	public List<Quotation> findByInvestmentFundWithDateLimit(GpwCompany company, GregorianCalendar startDate, GregorianCalendar endDate);
	
	/** zwaraca aktualna cene akcji dla danej spolki */
	public Quotation findActualQuotationByCompany(GpwCompany company);
}
