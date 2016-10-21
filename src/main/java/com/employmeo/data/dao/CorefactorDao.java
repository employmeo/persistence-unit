package com.employmeo.data.dao;

import java.util.List;

import com.employmeo.data.model.Corefactor;
import com.employmeo.data.model.identifier.CorefactorId;


public interface CorefactorDao extends Dao<CorefactorId, Corefactor> {

	public List<Corefactor> findAll();
}
