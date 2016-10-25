package com.employmeo.data.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Corefactor;
import com.employmeo.data.model.identifier.CorefactorId;
import com.employmeo.data.repository.CorefactorRepository;

import lombok.NonNull;
 
@Service
@Transactional
public class CorefactorServiceImpl implements CorefactorService {
	private static final Logger log = LoggerFactory.getLogger(CorefactorServiceImpl.class);
	
	@Autowired
	private CorefactorRepository corefactorRepository;	

	@Override
	public Iterable<Corefactor> getAllCorefactors() {
		Iterable<Corefactor> corefactors = corefactorRepository.findAll();
		log.debug("Retrieved all {} corefactors", corefactors);
		
		return corefactors;
	}

	@Override
	public Corefactor findCorefactorById(@NonNull CorefactorId id) {
		Corefactor corefactor = corefactorRepository.findOne(id.getLongValue());
		log.debug("Retrieved for id {} entity {}", id, corefactor);
		
		return corefactor;
	}

}
