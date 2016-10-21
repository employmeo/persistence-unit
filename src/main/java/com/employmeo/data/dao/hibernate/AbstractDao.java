package com.employmeo.data.dao.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.employmeo.data.dao.Dao;
import com.employmeo.data.model.identifier.Identity;

public abstract class AbstractDao<PK extends Identity, T> implements Dao<PK, T> {

	public AbstractDao(){
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

