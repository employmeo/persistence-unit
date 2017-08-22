package com.employmeo.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Corefactor;
import com.employmeo.data.repository.CorefactorRepository;

import jersey.repackaged.com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CorefactorServiceImpl implements CorefactorService {

	@Autowired
	private CorefactorRepository corefactorRepository;

	@Override
	@Cacheable(value="allcorefactors")
	public List<Corefactor> getAllCorefactors() {
		List<Corefactor> corefactors = Lists.newArrayList(corefactorRepository.findAll(new Sort(Direction.ASC, "id")));
		log.debug("Retrieved all {} corefactors", corefactors.size());

		return corefactors;
	}

	@Override
	@Cacheable(value="corefactors")
	public Corefactor findCorefactorById(@NonNull Long corefactorId) {
		Corefactor corefactor = corefactorRepository.findOne(corefactorId);
		log.debug("Retrieved for id {} entity {}", corefactorId, corefactor);

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

	@Override
	public void delete(Long corefactorId) {
		corefactorRepository.delete(corefactorId);
		log.debug("Deleted corefactor {}", corefactorId);
	}

	@Override
	@CacheEvict(allEntries=true,cacheNames={"corefactors","allcorefactors"})
	public void clearCache() {
		// does this empy all caches?	
	}

}
