package com.employmeo.data.dao.hibernate;

//import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.employmeo.data.dao.Dao;
import com.employmeo.data.model.identifier.Identity;

public abstract class AbstractDao<PK extends Identity, T> implements Dao<PK, T> {

	//private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public AbstractDao(){
		//this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	@PersistenceContext
	EntityManager entityManager;

	public EntityManager getEntityManager(){
		return this.entityManager;
	}

	@Override
	public void persist(T entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(T entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(T entity) {
		entityManager.remove(entity);
	}

}

