package com.victor.dao;

import java.util.List;

import com.victor.entity.BaseEntity;

public interface BaseEntityDAO<T extends BaseEntity> {

	public T findById(Long id);
	
	public List<T> findAll();

	public void saveOrUpdate(T entity);

	public void saveOrUpdate(List<T> entityList);

	
}
