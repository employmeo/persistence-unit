package com.employmeo.data.dao.hibernate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.dao.CorefactorDao;

import com.employmeo.data.model.Corefactor;
import com.employmeo.data.model.identifier.CorefactorId;
 
@Repository("corefactorDao")
@Transactional
public class CorefactorDaoImpl extends AbstractDao<CorefactorId, Corefactor> implements CorefactorDao {
	private static final Logger log = LoggerFactory.getLogger(CorefactorDaoImpl.class);

	@Override
	public Corefactor findById(CorefactorId key) {
		return entityManager.find(Corefactor.class, key.getLongValue());
	}

	@Override
	public List<Corefactor> findAll() {
		List<Corefactor> corefactors = entityManager
			.createNamedQuery("Corefactor.findAll", Corefactor.class)
			.getResultList();
		log.trace("Retrieved all corefactors: {}", corefactors);
		
		return corefactors;
	}

}

