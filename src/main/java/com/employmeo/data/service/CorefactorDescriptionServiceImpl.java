package com.employmeo.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.CorefactorDescription;
import com.employmeo.data.repository.CorefactorDescriptionRepository;

import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CorefactorDescriptionServiceImpl implements CorefactorDescriptionService {

	@Autowired
	private CorefactorDescriptionRepository corefactorDescriptionRepository;

	@Override
	public List<CorefactorDescription> getAll() {
//		List<CorefactorDescription> corefactorDescriptions = Lists.newArrayList(corefactorDescriptionRepository.findAll(new Sort(Direction.ASC, "id")));
		List<CorefactorDescription> corefactorDescriptions = Lists.newArrayList(corefactorDescriptionRepository.findAll(Sort.by(Direction.ASC, "id")));
		log.debug("Retrieved all {} corefactor descriptions", corefactorDescriptions.size());

		return corefactorDescriptions;
	}

	@Override
	public CorefactorDescription getById(@NonNull Long id) {
		CorefactorDescription corefactorDescription = corefactorDescriptionRepository.findById(id).get();
		//CorefactorDescription corefactorDescription = corefactorDescriptionRepository.findOne(id);
		log.debug("Retrieved for id {} entity {}", id, corefactorDescription);

		return corefactorDescription;
	}

	@Override
	public CorefactorDescription save(@NonNull CorefactorDescription corefactorDescription) {
		log.debug("Saving corefactorDescription {}", corefactorDescription);
		CorefactorDescription savedCorefactorDescription = corefactorDescriptionRepository.save(corefactorDescription);
		log.debug("Saved corefactorDescription {}", savedCorefactorDescription);

		return savedCorefactorDescription;
	}

	@Override
	public void delete(Long id) {
		corefactorDescriptionRepository.deleteById(id);
		//corefactorDescriptionRepository.delete(id);
		log.info("Deleted corefactorDescription {}", id);
	}


}
