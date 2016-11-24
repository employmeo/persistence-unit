package com.employmeo.data.service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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
	@Autowired
	GraderRepository graderRepository;

	private static final Integer DEFAULT_PAGE_NUMBER = 1;
	private static final Integer DEFAULT_PAGE_SIZE = 100;



	@Override
	public Respondant getRespondant(@NonNull UUID respondantUuid) {
		Respondant respondant = respondantRepository.findByRespondantUuid(respondantUuid);
		log.debug("Respondant by uuid '{}' : ", respondantUuid, respondant);

		return respondant;
	}

	@Override
	public Respondant getRespondantByAccountSurveyIdAndPayrollId(@NonNull Long accountSurveyId, @NonNull String payrollId) {
		log.debug("Respondant account survey {} and payrollId {}", accountSurveyId, payrollId);
		return respondantRepository.findByAccountSurveyIdAndPayrollId(accountSurveyId, payrollId);
	}

	@Override
	public Respondant getRespondantByAtsId(String atsId) {
		log.debug("Respondant by atsId {}", atsId);
		return respondantRepository.findByAtsId(atsId);
	}

	@Override
	public Respondant getRespondantByAccountIdAndAtsId(Long accountId, String atsId) {
		log.debug("Respondant account {} and atsId {}", accountId, atsId);
		return respondantRepository.findByAccountIdAndAtsId(accountId, atsId);
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

	@Override
	public Set<Response> getResponses(@NonNull UUID respondantUuid) {
		Respondant respondant = respondantRepository.findByRespondantUuid(respondantUuid);
		log.debug("Retrieved for id {} entity {}", respondantUuid, respondant);

		return respondant.getResponses();
	}

	@Override
	public Page<Respondant> getBySearchParams(
			@NonNull Long accountId,
			@NonNull Integer statusLow,
			@NonNull Integer statusHigh,
			Long locationId,
			Long positionId,
			@NonNull Timestamp fromDate,
			@NonNull Timestamp toDate) {

		return getBySearchParams(accountId,
				   statusLow, statusHigh,
				   locationId,
				   positionId,
				   fromDate, toDate,
				   DEFAULT_PAGE_NUMBER,
				   DEFAULT_PAGE_SIZE);
	}

	@Override
	public Page<Respondant> getBySearchParams(
			@NonNull Long accountId,
			@NonNull Integer statusLow,
			@NonNull Integer statusHigh,
			Long locationId,
			Long positionId,
			@NonNull Timestamp fromDate,
			@NonNull Timestamp toDate,
			@NonNull @Min(value = 1) Integer pageNumber,
			@NonNull @Min(value = 1) @Max(value = 500) Integer pageSize
			) {

		Pageable  pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "id");

		Page<Respondant> respondants = null;

		if ((locationId != null) && (positionId != null)) {
			respondants = respondantRepository.findAllByAccountIdAndLocationIdAndPositionIdAndRespondantStatusBetweenAndCreatedDateBetween(accountId, locationId, positionId, statusLow, statusHigh, fromDate, toDate, pageRequest);
		} else if ((locationId == null) && (positionId == null)) {
			respondants = respondantRepository.findAllByAccountIdAndRespondantStatusBetweenAndCreatedDateBetween(accountId, statusLow, statusHigh, fromDate, toDate, pageRequest);
		} else if (locationId == null) {
			respondants = respondantRepository.findAllByAccountIdAndPositionIdAndRespondantStatusBetweenAndCreatedDateBetween(accountId, positionId, statusLow, statusHigh, fromDate, toDate, pageRequest);
		} else {
			respondants = respondantRepository.findAllByAccountIdAndLocationIdAndRespondantStatusBetweenAndCreatedDateBetween(accountId, locationId, statusLow, statusHigh, fromDate, toDate, pageRequest);
		}



	    return respondants;
	};

	@Override
	public List<Respondant> getAnalysisPendingRespondants() {
		List<Integer> scoringEligibleRespondantStatuses = Arrays.asList(Respondant.STATUS_COMPLETED, Respondant.STATUS_SCORED);
		List<Respondant> scoringEligibleRespondants = respondantRepository.findAllByRespondantStatusInOrderByFinishTimeDesc(scoringEligibleRespondantStatuses);

		log.debug("Returning {} scoring eligible respondants", scoringEligibleRespondants.size());
		return scoringEligibleRespondants;
	}

	@Override
	public List<Respondant> getGraderBasedScoringPendingRespondants() {
		List<Integer> ungradedRespondantStatuses = Arrays.asList(Respondant.STATUS_UNGRADED);
		List<Respondant> ungradedRespondants = respondantRepository.findAllByRespondantStatusInOrderByFinishTimeDesc(ungradedRespondantStatuses);
		log.debug("Found {} respondants in ungraded status", ungradedRespondants.size());

		List<Respondant> scoringEligibleRespondants = ungradedRespondants.stream()
														.filter(ungradedRespondant -> {
															List<Grader> graders = graderRepository.findAllByRespondantId(ungradedRespondant.getId());

															Optional<Grader> incompleteGrader = graders.stream()
																									.filter(g ->
																										null == g.getStatus() ||
																										g.getStatus() != Grader.STATUS_COMPLETED ||
																										g.getStatus() != Grader.STATUS_IGNORED)
																									.findAny();

															return !incompleteGrader.isPresent();
														})
														.collect(Collectors.toList());

		scoringEligibleRespondants.forEach(eligibleRespondant -> log.debug("Eligible respondant with all graders fulfilled: {}", eligibleRespondant.getId()));

		log.debug("Found {} respondants who are ungraded, with all graders fulfilled", scoringEligibleRespondants.size());
		return scoringEligibleRespondants;
	}
}
