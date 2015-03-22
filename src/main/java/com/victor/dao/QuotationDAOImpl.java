package com.victor.dao;

import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.victor.entity.GpwCompany;
import com.victor.entity.Quotation;
import com.victor.utils.DateFormat;
import com.victor.utils.DateUtils;

/**
 * @author Wiktor Rosinski
 *
 */
@Repository
public class QuotationDAOImpl extends BaseEntityDAOImpl<Quotation> implements QuotationDAO{
	

	/* (non-Javadoc) @see com.victor.dao.QuotationDAO#isQuotationForCompanyInDate(com.victor.entity.GpwCompany, java.lang.String) */
	@Override
	public boolean isQuotationForCompanyInDate(GpwCompany company,
			String stringDate, DateFormat dateFormat) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT EXISTS ( SELECT 1 FROM quotation where quotation.company_id = ");
		buffer.append(company.getId());
		buffer.append(" and quotation.date = '");
		switch(dateFormat){
		case DD_MM_YYYY:
			buffer.append(DateUtils.convertBetweenDateFormat(stringDate, DateFormat.DD_MM_YYYY, DateFormat.YYYY_MM_DD));
			break;
		case YYYY_MM_DD:
			buffer.append(stringDate);
			break;
		default:
			throw new RuntimeException();
		}
		buffer.append("')");
		Query query = session.createSQLQuery(buffer.toString());
		Boolean isResult = (Boolean) query.uniqueResult();
		if(isResult==null)
			isResult=false;
		return isResult;
	}

	/* (non-Javadoc) @see com.victor.dao.QuotationDAO#findByInvestmentFundWithDateLimit(com.victor.entity.GpwCompany, java.util.GregorianCalendar, java.util.GregorianCalendar) */
	@SuppressWarnings("unchecked")
	@Override
	public List<Quotation> findByInvestmentFundWithDateLimit(
			GpwCompany company, GregorianCalendar startDate,
			GregorianCalendar endDate) {
		
		Query query=session.getNamedQuery("quotationByCompanyAndDates");
		query.setLong("companyId", company.getId());
		query.setCalendar("startDate", startDate);
		query.setCalendar("endDate", endDate);
		return query.list();
	}

	/* (non-Javadoc) @see com.victor.dao.QuotationDAO#findActualQuotationByCompany(com.victor.entity.GpwCompany) */
	@Override
	public Quotation findActualQuotationByCompany(GpwCompany company) {
		Query query=session.getNamedQuery("actualQuotationByCompany");
		query.setLong("companyId", company.getId());
		query.setMaxResults(1);
		return (Quotation) query.uniqueResult();
	}

}
