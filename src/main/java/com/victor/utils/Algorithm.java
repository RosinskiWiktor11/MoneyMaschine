package com.victor.utils;

import java.math.BigDecimal;
import java.util.List;

import com.victor.entity.Value;

/**
 * @author Wiktor
 *
 */
public abstract class Algorithm {

	protected String name;
	protected String paramsDescription;

	public abstract boolean openLongTransaction();

	public abstract boolean closeLongTransaction();

	public abstract boolean openShortTransaction();

	public abstract boolean closeShortTransaction();
	
	public abstract int getLengthListToLoad();
	
	public abstract boolean loadAlgorithmToTest(List<? extends Value> priceListToLoad);

	public abstract void putPrice(Value value);
	
	public abstract List<? extends Algorithm> getRandomAlgorithmVersion(int numberOfVersions);
	
	public abstract void loadParameter(String params);
	
	public abstract BigDecimal[] getParamsValues();
	
	public abstract int getParamNumber();
	
	public abstract boolean equals(Object o);

	public String toString() {
		return name + ";" + paramsDescription;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParamsDescription() {
		return paramsDescription;
	}

	public void setParamsDescription(String paramsDescription) {
		this.paramsDescription = paramsDescription;
	}
}
