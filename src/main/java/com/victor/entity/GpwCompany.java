package com.victor.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Wiktor Rosinki<br/>
 * 		spolka akcyjna notowana na warszawskiej gieldzie GPW
 *
 */
@NamedQueries({
	@NamedQuery(name = "findAllActiveGpwCompany", query = "FROM GpwCompany WHERE active = TRUE ORDER BY name ASC "),
	@NamedQuery(name = "findBoughtCompanyByCustomer", query = "SELECT com FROM GpwCompany com, CustomerGpwTransaction transaction WHERE transaction.closePrice = null AND transaction.owner.id= :customerId AND transaction.company.id = com.id ORDER BY openDate DESC ")
	})
@Entity
@Table(name="GPW_COMPANY")
public class GpwCompany extends FinancialInstrument{
	
	/** nazwa spolki */
	private String name;
	/** skrot nazwy */
	private String shortName;
	/** kod isin */
	private String isin;
	
	private boolean active;
	
	//RELATIONS
	@OneToMany(mappedBy = "company", cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE })
	List<Quotation> quotationHistory;
	@ManyToOne
	@JoinColumn(name="opening_algorithm")
	GpwAlgorithm openingAlgorithm;
	@ManyToOne
	@JoinColumn(name="closing_algorithm")
	GpwAlgorithm closingAlgorithm;
	@ManyToOne
	Currency currency;
	@Embedded
	private SignalWeight weight;
	
	
	/**{@link GpwCompany#name}*/
	public String getName() {
		return name;
	}

	/**{@link GpwCompany#name}*/
	public void setName(String name) {
		this.name = name;
	}

	/**{@link GpwCompany#shortName}*/
	public String getShortName() {
		return shortName;
	}

	/**{@link GpwCompany#shortName}*/
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**{@link GpwCompany#isin}*/
	public String getIsin() {
		return isin;
	}

	/**{@link GpwCompany#isin}*/
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**{@link GpwCompany#quotationHistory}*/
	public List<Quotation> getQuotationHistory() {
		return quotationHistory;
	}

	/**{@link GpwCompany#quotationHistory}*/
	public void setQuotationHistory(List<Quotation> quotationHistory) {
		this.quotationHistory = quotationHistory;
	}

	/**{@link GpwCompany#openingAlgorithm}*/
	public GpwAlgorithm getOpeningAlgorithm() {
		return openingAlgorithm;
	}

	/**{@link GpwCompany#openingAlgorithm}*/
	public void setOpeningAlgorithm(GpwAlgorithm openingAlgorithm) {
		this.openingAlgorithm = openingAlgorithm;
	}

	/**{@link GpwCompany#closingAlgorithm}*/
	public GpwAlgorithm getClosingAlgorithm() {
		return closingAlgorithm;
	}

	/**{@link GpwCompany#closingAlgorithm}*/
	public void setClosingAlgorithm(GpwAlgorithm closingAlgorithm) {
		this.closingAlgorithm = closingAlgorithm;
	}

	/**{@link GpwCompany#currency}*/
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**{@link GpwCompany#currency}*/
	public Currency getCurrency() {
		return currency;
	}

	/**{@link GpwCompany#active}*/
	public boolean isActive() {
		return active;
	}

	/**{@link GpwCompany#active}*/
	public void setActive(boolean active) {
		this.active = active;
	}

	/**{@link GpwCompany#weight}*/
	public SignalWeight getWeight() {
		return weight;
	}

	/**{@link GpwCompany#weight}*/
	public void setWeight(SignalWeight weight) {
		this.weight = weight;
	}
	
	
	public String toString(){
		return name;
	}
}
