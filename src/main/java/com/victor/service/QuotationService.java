package com.victor.service;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.dao.QuotationDAO;
import com.victor.entity.GpwCompany;
import com.victor.entity.Quotation;
import com.victor.utils.DateFormat;

/**
 * @author Wiktor Rosinski
 *
 */
@Service
public class QuotationService {
	
	@Autowired
	private QuotationDAO quotationDAO;

	/**
	 * @param id
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findById(java.lang.Long)
	 */
	public Quotation findById(Long id) {
		return quotationDAO.findById(id);
	}

	/**
	 * @param entity
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(com.victor.entity.BaseEntity)
	 */
	public void saveOrUpdate(Quotation entity) {
		quotationDAO.saveOrUpdate(entity);
	}

	/**
	 * @param entityList
	 * @see com.victor.dao.BaseEntityDAO#saveOrUpdate(java.util.List)
	 */
	public void saveOrUpdate(List<Quotation> entityList) {
		quotationDAO.saveOrUpdate(entityList);
	}

	/**
	 * @param company
	 * @param stringDate
	 * @param dateFormat
	 * @return
	 * @see com.victor.dao.QuotationDAO#isQuotationForCompanyInDate(com.victor.entity.GpwCompany, java.lang.String, com.victor.utils.DateFormat)
	 */
	public boolean isQuotationForCompanyInDate(GpwCompany company,
			String stringDate, DateFormat dateFormat) {
		Boolean resu= quotationDAO.isQuotationForCompanyInDate(company, stringDate,
				dateFormat);
		return resu;
	}

	/**
	 * @param company
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.victor.dao.QuotationDAO#findByInvestmentFundWithDateLimit(com.victor.entity.GpwCompany, java.util.GregorianCalendar, java.util.GregorianCalendar)
	 */
	public List<Quotation> findByGpwCompanyWithDateLimit(
			GpwCompany company, GregorianCalendar startDate,
			GregorianCalendar endDate) {
		return quotationDAO.findByInvestmentFundWithDateLimit(company,
				startDate, endDate);
	}

	/**
	 * @return
	 * @see com.victor.dao.BaseEntityDAO#findAll()
	 */
	public List<Quotation> findAll() {
		return quotationDAO.findAll();
	}

	/**
	 * @param company
	 * @param startDate
	 * @param endDate
	 * @return
	 * @see com.victor.dao.QuotationDAO#findByInvestmentFundWithDateLimit(com.victor.entity.GpwCompany, java.util.GregorianCalendar, java.util.GregorianCalendar)
	 */
	public List<Quotation> findByInvestmentFundWithDateLimit(
			GpwCompany company, GregorianCalendar startDate,
			GregorianCalendar endDate) {
		return quotationDAO.findByInvestmentFundWithDateLimit(company,
				startDate, endDate);
	}

	/**
	 * @param company
	 * @return
	 * @see com.victor.dao.QuotationDAO#findActualQuotationByCompany(com.victor.entity.GpwCompany)
	 */
	public Quotation findActualQuotationByCompany(GpwCompany company) {
		return quotationDAO.findActualQuotationByCompany(company);
	}

	
}
