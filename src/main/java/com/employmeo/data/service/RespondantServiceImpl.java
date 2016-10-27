package com.employmeo.data.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.*;
import com.employmeo.data.repository.RespondantRepository;
import com.employmeo.data.repository.RespondantScoreRepository;

import jersey.repackaged.com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RespondantServiceImpl implements RespondantService {

	@Autowired
	private RespondantRepository respondantRepository;
	@Autowired
	private RespondantScoreRepository respondantScoreRepository;


	@Override
	public Set<Respondant> getAllRespondants() {
		Set<Respondant> respondants = Sets.newHashSet(respondantRepository.findAll());
		log.debug("Retrieved all {} respondants", respondants);

		return respondants;
	}

	@Override
	public Respondant save(@NonNull Respondant respondant) {
		Respondant savedRespondant = respondantRepository.save(respondant);
		log.debug("Saved respondant {}", respondant);

		return savedRespondant;
	}

	@Override
	public Respondant getRespondantById(@NonNull Long respondantId) {
		Respondant respondant = respondantRepository.findOne(respondantId);
		log.debug("Retrieved for id {} entity {}", respondantId, respondant);

		return respondant;
	}

	@Override
	public Set<RespondantScore> getAllRespondantScores() {
		Set<RespondantScore> respondantScores = Sets.newHashSet(respondantScoreRepository.findAll());
		log.debug("Retrieved all {} respondantScores", respondantScores);

		return respondantScores;
	}

	@Override
	public RespondantScore save(@NonNull RespondantScore respondantScore) {
		RespondantScore savedRespondantScore = respondantScoreRepository.save(respondantScore);
		log.debug("Saved respondantScore {}", respondantScore);

		return savedRespondantScore;
	}

	@Override
	public RespondantScore getRespondantScoreById(@NonNull RespondantScorePK respondantScorePK) {
		RespondantScore respondantScore = respondantScoreRepository.findOne(respondantScorePK);
		log.debug("Retrieved for id {} entity {}", respondantScorePK, respondantScore);

		return respondantScore;
	}
}
