package com.employmeo.data.dao;

import com.employmeo.data.model.identifier.Identity;
 
public interface Dao<PK extends Identity, T> {

	public abstract T findById(PK key);
	
	public abstract void persist(T entity);

	public abstract void update(T entity);

	public abstract void delete(T entity);


	
}
