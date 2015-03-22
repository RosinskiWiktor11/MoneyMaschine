package com.victor.dao;

import java.util.List;

import com.victor.entity.GpwAlgorithm;
import com.victor.entity.GpwCompany;

/**
 * @author Wiktor Rosinski
 *
 */
public interface GpwAlgorithmDAO extends BaseEntityDAO<GpwAlgorithm>{

	/** wyszukuje wszystkie algorytmy ktore byly przypisane do spolki */
	public List<GpwAlgorithm> findAllByCompany(GpwCompany company);
	/** wyszukuje aktywny algorytm otwierajacy */
	public GpwAlgorithm findActiveOpenAlgorithm(GpwCompany company);
	/** wyszukuje aktywny algorytm zamykajacy */
	public GpwAlgorithm findActiveCloseAlgorithm(GpwCompany company);
}
