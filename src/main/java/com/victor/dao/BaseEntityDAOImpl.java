package com.victor.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.ManagedBean;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.victor.entity.BaseEntity;

@ManagedBean
@Repository
public class BaseEntityDAOImpl<T extends BaseEntity> implements
		BaseEntityDAO<T> {

	@Autowired
	protected Session session;

	protected Class<T> persistentClass;

	// ***********************************************************
	// ******************** Constuctor **************************
	// ***********************************************************

	@SuppressWarnings("unchecked")
	public BaseEntityDAOImpl() {
		ParameterizedType parameterizedType = (ParameterizedType) this
				.getClass().getGenericSuperclass();
		persistentClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		Query query=session.createQuery("from "+persistentClass.getName());
		return query.list();
	}

	/*
	 * (non-Javadoc) @see
	 * com.victor.skandia.dao.BaseEntityDAO#findById(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findById(Long id) {
		return (T) session.get(persistentClass, id);
	}

	/*
	 * (non-Javadoc) @see
	 * com.victor.skandia.dao.BaseEntityDAO#saveOrUpdate(com.victor
	 * .skandia.entity.BaseEntity)
	 */
	@Override
	public void saveOrUpdate(T entity) {
		session.beginTransaction();
		session.persist(entity);
		session.getTransaction().commit();
	}

	/*
	 * (non-Javadoc) @see
	 * com.victor.skandia.dao.BaseEntityDAO#saveOrUpdate(java.util.List)
	 */
	@Override
	public void saveOrUpdate(List<T> entityList) {
		session.beginTransaction();
		for (T entity : entityList) {
			session.persist(entity);
		}
		session.getTransaction().commit();
	}

}
