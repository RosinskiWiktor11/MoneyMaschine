package com.victor.dao;

import java.util.List;

import com.victor.entity.Customer;

public interface CustomerDAO extends BaseEntityDAO<Customer> {

	/** wyszukuje uzytkownika po loginie i hasle, jezeli nie znajdzie zwraca null */
	public Customer loginProcess(String login, String passwrod);
	
	/** wyszukuje wszystkich uzytkownikow */
	public List<Customer> findAll();
	
	/** wyszukuje klientow przedstawiciela wskazanego w argumencie */
	public List<Customer> findByAgent(Customer customer);
}
