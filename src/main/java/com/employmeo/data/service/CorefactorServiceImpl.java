package com.employmeo.data.service;

import java.util.Set;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Corefactor;
import com.employmeo.data.repository.CorefactorRepository;

import jersey.repackaged.com.google.common.collect.Sets;
import lombok.NonNull;

@Service
@Transactional
public class CorefactorServiceImpl implements CorefactorService {
	private static final Logger log = LoggerFactory.getLogger(CorefactorServiceImpl.class);

	@Autowired
	private CorefactorRepository corefactorRepository;

	@Override
	public Set<Corefactor> getAllCorefactors() {
		Set<Corefactor> corefactors = Sets.newHashSet(corefactorRepository.findAll());
		log.debug("Retrieved all {} corefactors", corefactors);

		return corefactors;
	}

	@Override
	public Corefactor findCorefactorById(@NonNull Long corefactorId) {
		Corefactor corefactor = corefactorRepository.findOne(corefactorId);
		log.debug("Retrieved for id {} entity {}", corefactorId, corefactor);

		return corefactor;
	}

	@Override
	public Corefactor save(@NonNull Corefactor corefactor) {
		Corefactor savedCorefactor = corefactorRepository.save(corefactor);
		log.debug("Saved corefactor {}", corefactor);

		return savedCorefactor;
	}

}
