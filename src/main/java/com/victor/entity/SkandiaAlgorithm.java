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
 * @author Wiktor
 *
 */
/**
 * @author Wiktor
 *
 */
@NamedQueries({
	@NamedQuery(name="findAllByFund", query="FROM SkandiaAlgorithm WHERE investmentFund.id = :fundId"),
	@NamedQuery(name="findOpenActiveByFund", query="FROM SkandiaAlgorithm WHERE investmentFund.id = :fundId AND active = TRUE AND openingAlgorithm = TRUE"),
	@NamedQuery(name="findCloseActiveByFund", query="FROM SkandiaAlgorithm WHERE investmentFund.id = :fundId AND active = TRUE AND openingAlgorithm = FALSE")
})
@Entity
@Table(name="SKANDIA_ALGORITHM")
public class SkandiaAlgorithm extends BaseEntity {
	
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
	
	//RELATIONS
	@ManyToOne
	private InvestmentFund investmentFund;
	
	/** algorytm ktorego metody beda delegowane */
	transient private Algorithm algorithm;
	
	public SkandiaAlgorithm(){

	}

	/** {@link SkandiaAlgorithm} */
	public SkandiaAlgorithm(Class<? extends Algorithm> algorythmClass, InvestmentFund fund) {
		try {
			this.investmentFund=fund;
			algorithm = algorythmClass.getConstructor().newInstance();
			name=algorithm.getName();
			parameter=algorithm.getParamsDescription();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	public SkandiaAlgorithm(SkandiaAlgorithm skandiaAlgorithm){
		this.active=skandiaAlgorithm.isActive();
		this.openingAlgorithm=skandiaAlgorithm.isOpeningAlgorithm();
		this.algorithm=skandiaAlgorithm.getAlgorithm();
		this.createDate=skandiaAlgorithm.getCreateDate();
		this.investmentFund=skandiaAlgorithm.getInvestmentFund();
		this.name=skandiaAlgorithm.getName();
		this.parameter=skandiaAlgorithm.getParameter();
	}
	
	/** {@link SkandiaAlgorithm} */
	public SkandiaAlgorithm(Algorithm algorithm) {
		this.algorithm=algorithm;
		this.name=algorithm.getName();
		this.parameter=algorithm.getParamsDescription();
	}
	
	/** przepisuje dane z algorytmu dla ktorego jest delegatem na wlasne pola */
	public void loadDataFromAlgorithm(){
		name=algorithm.getName();
		parameter=algorithm.getParamsDescription();
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

	public String toString() {
		return algorithm.toString();
	}

	public boolean openTransaction() {
		if(algorithm==null)
			return false;
		return algorithm.openLongTransaction();
	}

	public boolean closeTransaction() {
		if(algorithm==null)
			return false;
		return algorithm.closeLongTransaction();
	}

	public List<SkandiaAlgorithm> getRandomAlgorithmVersion(int numberOfVersions) {
		List<? extends Algorithm>algorithmList= algorithm.getRandomAlgorithmVersion(numberOfVersions);
		List<SkandiaAlgorithm> skandiaAlgorithmList=new ArrayList<SkandiaAlgorithm>();
		for(Algorithm algorithm: algorithmList){
			skandiaAlgorithmList.add(new SkandiaAlgorithm(algorithm));
		}
		return skandiaAlgorithmList;
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

	public int getLengthListToLoad() {
		return algorithm.getLengthListToLoad();
	}

	public boolean loadAlgorithmToTest(List<? extends Value> priceListToLoad) {
		return algorithm.loadAlgorithmToTest(priceListToLoad);
	}

	public void putPrice(FundValue value) {
		algorithm.putPrice(value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public GregorianCalendar getCreateDate() {
		return createDate;
	}

	public void setCreateDate(GregorianCalendar createDate) {
		this.createDate = createDate;
	}

	public GregorianCalendar getDesactivateDate() {
		return desactivateDate;
	}

	public void setDesactivateDate(GregorianCalendar desactivateDate) {
		this.desactivateDate = desactivateDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public InvestmentFund getInvestmentFund() {
		return investmentFund;
	}

	public void setInvestmentFund(InvestmentFund investmentFund) {
		this.investmentFund = investmentFund;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	/**{@link SkandiaAlgorithm#openingAlgorithm}*/
	public boolean isOpeningAlgorithm() {
		return openingAlgorithm;
	}

	/**{@link SkandiaAlgorithm#openingAlgorithm}*/
	public void setOpeningAlgorithm(boolean openingAlgorithm) {
		this.openingAlgorithm = openingAlgorithm;
	}

	
}
	

 	
