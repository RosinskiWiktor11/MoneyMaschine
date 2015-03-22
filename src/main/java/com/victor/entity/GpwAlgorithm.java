package com.victor.entity;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.victor.utils.Algorithm;

/**
 * @author Wiktor Rosinski<br/>
 * 		algorytm do analizowania akcji na rynku GPW
 *
 */
@NamedQueries({
	@NamedQuery(name="gpwAlgorithmAllByCompany", query="FROM GpwAlgorithm WHERE company.id= :companyId"),
	@NamedQuery(name="findGpwAlgorithmOpenActiveByFund", query="FROM GpwAlgorithm WHERE company.id = :companyId AND active = TRUE AND openingAlgorithm = TRUE"),
	@NamedQuery(name="findGpwAlgorithmCloseActiveByFund", query="FROM GpwAlgorithm WHERE company.id = :companyId AND active = TRUE AND openingAlgorithm = FALSE")
})
@Entity
@Table(name="GPW_ALGORITHM")
public class GpwAlgorithm extends AlgorithmEntity{

	/** nazwa algorytmu */
	private String name;
	/** parametry algorytmu */
	private String parameter;
	/** data utoworzenia algorytmu */
	private GregorianCalendar createDate;
	/** data dezaktywacji */
	private GregorianCalendar desactivateDate;
	/** czy jest aktywny */
	private boolean active;
	/** czy jest algorytmem otwierajacym */
	private boolean openingAlgorithm;
	
	/** algorytm ktorego metody beda delegowane */
	transient private Algorithm algorithm;
	
	//RELATIONS
	@ManyToOne
	GpwCompany company;
	
	/** {@link GpwAlgorithm} */
	public GpwAlgorithm(){}

	/** {@link GpwAlgorithm} */
	public GpwAlgorithm(Class<? extends Algorithm> algorythmClass, GpwCompany company) {
		try {
			this.company=company;
			algorithm = algorythmClass.getConstructor().newInstance();
			name=algorithm.getName();
			parameter=algorithm.getParamsDescription();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	public GpwAlgorithm(GpwAlgorithm gpwAlgorithm){
		this.active=gpwAlgorithm.isActive();
		this.openingAlgorithm=gpwAlgorithm.isOpeningAlgorithm();
		this.algorithm=gpwAlgorithm.getAlgorithm();
		this.createDate=gpwAlgorithm.getCreateDate();
		this.company=gpwAlgorithm.getCompany();
		this.name=gpwAlgorithm.getName();
		this.parameter=gpwAlgorithm.getParameter();
	}
	
	/** {@link SkandiaAlgorithm} */
	public GpwAlgorithm(Algorithm algorithm) {
		this.algorithm=algorithm;
		this.name=algorithm.getName();
		this.parameter=algorithm.getParamsDescription();
	}
	
	/** tworzy algorytm, dla ktorego jest delegatem na podstawie wlasnych danych */
	public void loadAlgorithm(){
		try {
			//TODO do usuniecia
			if(name.equals("MACD_Simple"))
				name="MACDsimple";
			@SuppressWarnings("unchecked")
			Class<? extends Algorithm> algorithmClass = (Class<? extends Algorithm>) Class.forName("algorithm."+name);
			algorithm = algorithmClass.getConstructor().newInstance();		
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		algorithm.setName(name);
		algorithm.loadParameter(parameter);
	}
	
	public boolean loadAlgorithmToTest(List<? extends Value> priceListToLoad) {
		return algorithm.loadAlgorithmToTest(priceListToLoad);
	}
	
	public int getLengthListToLoad() {
		return algorithm.getLengthListToLoad();
	}
	
	public void putPrice(Quotation value) {
		algorithm.putPrice(value);
	}
	
	public String toString() {
		return algorithm.toString();
	}

	public boolean openTransaction() {
		return algorithm.openLongTransaction();
	}

	public boolean closeTransaction() {
		return algorithm.closeLongTransaction();
	}
	
	/** przepisuje dane z algorytmu dla ktorego jest delegatem na wlasne pola */
	public void loadDataFromAlgorithm(){
		name=algorithm.getName();
		parameter=algorithm.getParamsDescription();
	}
	
	public List<GpwAlgorithm> getRandomAlgorithmVersion(int numberOfVersions) {
		List<? extends Algorithm>algorithmList= algorithm.getRandomAlgorithmVersion(numberOfVersions);
		List<GpwAlgorithm> gpwAlgorithmList=new ArrayList<GpwAlgorithm>();
		for(Algorithm algorithm: algorithmList){
			gpwAlgorithmList.add(new GpwAlgorithm(algorithm));
		}
		return gpwAlgorithmList;
	}
	
	
	/**
	 * @return
	 * @see com.victor.utils.Algorithm#getParamsValues()
	 */
	public BigDecimal[] getParamsValues() {
		return algorithm.getParamsValues();
	}

	/**
	 * @return
	 * @see com.victor.utils.Algorithm#getParamNumber()
	 */
	public int getParamNumber() {
		return algorithm.getParamNumber();
	}
	
	/**{@link GpwAlgorithm#company}*/
	public GpwCompany getCompany() {
		return company;
	}
	/**{@link GpwAlgorithm#company}*/
	public void setCompany(GpwCompany company) {
		this.company = company;
	}
	
	/**{@link GpwAlgorithm#name}*/
	public String getName() {
		return name;
	}
	/**{@link GpwAlgorithm#name}*/
	public void setName(String name) {
		this.name = name;
	}
	/**{@link GpwAlgorithm#parameter}*/
	public String getParameter() {
		return parameter;
	}
	/**{@link GpwAlgorithm#parameter}*/
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	/**{@link GpwAlgorithm#createDate}*/
	public GregorianCalendar getCreateDate() {
		return createDate;
	}
	/**{@link GpwAlgorithm#createDate}*/
	public void setCreateDate(GregorianCalendar createDate) {
		this.createDate = createDate;
	}
	/**{@link GpwAlgorithm#desactivateDate}*/
	public GregorianCalendar getDesactivateDate() {
		return desactivateDate;
	}
	/**{@link GpwAlgorithm#desactivateDate}*/
	public void setDesactivateDate(GregorianCalendar desactivateDate) {
		this.desactivateDate = desactivateDate;
	}
	/**{@link GpwAlgorithm#active}*/
	public boolean isActive() {
		return active;
	}
	/**{@link GpwAlgorithm#active}*/
	public void setActive(boolean active) {
		this.active = active;
	}
	/**{@link GpwAlgorithm#openingAlgorithm}*/
	public boolean isOpeningAlgorithm() {
		return openingAlgorithm;
	}
	/**{@link GpwAlgorithm#openingAlgorithm}*/
	public void setOpeningAlgorithm(boolean openingAlgorithm) {
		this.openingAlgorithm = openingAlgorithm;
	}

	/**{@link GpwAlgorithm#algorithm}*/
	public Algorithm getAlgorithm() {
		return algorithm;
	}
	
	
}
