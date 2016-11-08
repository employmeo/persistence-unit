package com.employmeo.data.service;

import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.*;
import com.employmeo.data.repository.*;

import jersey.repackaged.com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RespondantServiceImpl implements RespondantService  {

	@Autowired
	private RespondantRepository respondantRepository;
	@Autowired
	private RespondantScoreRepository respondantScoreRepository;
	@Autowired
	private ResponseRepository responseRepository;
	@Autowired
	private QuestionRepository questionRepository;

	private static final Integer DEFAULT_PAGE_NUMBER = 1;
	private static final Integer DEFAULT_PAGE_SIZE = 10;



	@Override
	public Respondant getRespondant(@NonNull UUID respondantUuid) {
		Respondant respondant = respondantRepository.findByRespondantUuid(respondantUuid);
		log.debug("Respondant by uuid '{}' : ", respondantUuid, respondant);

		return respondant;
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
	public Page<Respondant> getByAccountId(Long accountId, @NonNull @Min(value = 1) Integer pageNumber, @NonNull @Min(value = 1) @Max(value = 100) Integer pageSize) {
		Pageable  pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "id");

		Page<Respondant> respondants = respondantRepository.findAllByAccountId(accountId, pageRequest);
		log.debug("Respondants for AccountId {} for pageNumber {}: {}", accountId, pageNumber, respondants);

		return respondants;
	}

	@Override
	public Page<Respondant> getByAccountId(@NonNull Long accountId) {
		return getByAccountId(accountId, DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
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

	@Override
	public Response saveResponse(@NonNull Response response) {
		Response savedResponse = responseRepository.save(response);
		log.debug("Saved response {}", response);

		return savedResponse;
	}

	@Override
	public Response saveResponse(@NonNull Long respondantId, @NonNull Long questionId, Integer responseValue, String responseText) {
		Respondant respondant = respondantRepository.findOne(respondantId);
		Question question = questionRepository.findOne(questionId);

		Response response = new Response();
		response.setQuestion(question);
		response.setQuestionId(questionId);
		response.setRespondant(respondant);
		response.setRespondantId(respondantId);
		response.setResponseText(responseText);
		response.setResponseValue(responseValue);

		Response savedResponse = responseRepository.save(response);
		log.debug("Saved response {}", savedResponse);

		return savedResponse;
	}
}
