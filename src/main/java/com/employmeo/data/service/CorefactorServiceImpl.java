package com.employmeo.data.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Corefactor;
import com.employmeo.data.repository.CorefactorRepository;

import jersey.repackaged.com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CorefactorServiceImpl implements CorefactorService {

	@Autowired
	private CorefactorRepository corefactorRepository;

	@Override
	public Set<Corefactor> getAllCorefactors() {
		Set<Corefactor> corefactors = Sets.newHashSet(corefactorRepository.findAll());
		log.debug("Retrieved all {} corefactors", corefactors.size());

		return corefactors;
	}

	@Override
	public Corefactor findCorefactorById(@NonNull Long corefactorId) {
		Corefactor corefactor = corefactorRepository.findOne(corefactorId);
		log.debug("Retrieved for id {} entity {}", corefactorId, corefactor.getName());

		return corefactor;
	}

	@Override
	public Corefactor save(@NonNull Corefactor corefactor) {
		log.debug("Saving corefactor {}", corefactor);
		Corefactor savedCorefactor = corefactorRepository.save(corefactor);
		log.debug("Saved corefactor {}", savedCorefactor);

		return savedCorefactor;
	}

	@Override
	public Corefactor getByForeignId(String foreignId) {
		Corefactor corefactor = corefactorRepository.findByForeignId(foreignId);
		log.debug("Retrieved for foreignId {}, corefactor: {}", foreignId, corefactor.getName());

		return corefactor;
	}

}
