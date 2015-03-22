package com.victor.skandia.model;

/**
 * @author Dell
 *
 */
public enum CustomerGroupType {

	ALL("Wszystkim klientom"),
	ALL_MY_CUSTOMERS("Wszystkim moim klientom"),
	ME("Tylko mi");
	
	private String name;
	
	private CustomerGroupType(String name){
		this.name=name;
	}
	public String toString(){
		return name;
	}
	
	public static CustomerGroupType getEnum(int i){
		return CustomerGroupType.values()[i];
	}
}
