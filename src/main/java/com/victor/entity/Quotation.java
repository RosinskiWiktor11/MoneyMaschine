package com.victor.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Wiktor Rosinski
 *
 */
@NamedQueries({
	@NamedQuery(name="quotationByCompanyAndDates", query="FROM Quotation WHERE company.id= :companyId AND date >= :startDate AND  date <= :endDate ORDER BY date ASC "),
	@NamedQuery(name="actualQuotationByCompany", query="FROM Quotation WHERE company.id= :companyId ORDER BY date DESC")
})
@Entity
@Table(name="QUOTATION")
public class Quotation extends Value{
	/** kurs otwarcia*/
	private BigDecimal openPrice;
	/** cena maksymalna*/
	private BigDecimal maxPrice;
	/** cena minimalna */
	private BigDecimal minPrice;
	/** wolumen */
	private BigDecimal volume;
	/** liczba transakcji*/
	private Integer transactionNumber;
	
	//RELATIONS
	@ManyToOne
	GpwCompany company;
	
	/**{@link Quotation#openPrice}*/
	public BigDecimal getOpenPrice() {
		return openPrice;
	}
	/**{@link Quotation#openPrice}*/
	public void setOpenPrice(BigDecimal openPrice) {
		this.openPrice = openPrice;
	}
	/**{@link Quotation#maxPrice}*/
	public BigDecimal getMaxPrice() {
		return maxPrice;
	}
	/**{@link Quotation#maxPrice}*/
	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}
	/**{@link Quotation#minPrice}*/
	public BigDecimal getMinPrice() {
		return minPrice;
	}
	/**{@link Quotation#minPrice}*/
	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}
	/**{@link Quotation#volume}*/
	public BigDecimal getVolume() {
		return volume;
	}
	/**{@link Quotation#volume}*/
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	/**{@link Quotation#company}*/
	public GpwCompany getCompany() {
		return company;
	}
	/**{@link Quotation#company}*/
	public void setCompany(GpwCompany company) {
		this.company = company;
	}
	/**{@link Value#value}*/
	public BigDecimal getClosePrice(){
		return super.getValue();
	}
	/**{@link Value#value}*/
	public void setClosePrice(BigDecimal closePrice){
		super.setValue(closePrice);
	}
	/**{@link Quotation#transactionNumber}*/
	public Integer getTransactionNumber() {
		return transactionNumber;
	}
	/**{@link Quotation#transactionNumber}*/
	public void setTransactionNumber(Integer transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	
	
}
