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
	private GraderRepository graderRepository;
	@Autowired
	private OutcomeRepository outcomeRepository;

	private static final Integer DEFAULT_PAGE_NUMBER = 1;
	private static final Integer DEFAULT_PAGE_SIZE = 100;
	private static final String AUDIO_SCORING = "audio";
	//private static final int REFERENCE_COREFACTOR = 43;

	@Override
	public Respondant getRespondant(@NonNull UUID respondantUuid) {
		Respondant respondant = respondantRepository.findByRespondantUuid(respondantUuid);
		log.debug("Respondant by uuid '{}' : ", respondantUuid, respondant);

		return respondant;
	}

	@Override
	public Respondant getRespondantByAccountSurveyIdAndPayrollId(@NonNull Long accountSurveyId, @NonNull String payrollId) {
		log.debug("Respondant account survey {} and payrollId {}", accountSurveyId, payrollId);
		
		List<Respondant> rs = respondantRepository.findAllByAccountSurveyIdAndPayrollIdOrderByCreatedDateDesc(accountSurveyId, payrollId);
		for (Respondant resp : rs) {
			if (resp.getRespondantStatus() < Respondant.STATUS_SCORED) return resp;
		}
		return null;
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
		Response savedResponse = null;
		
		// guard against duplicate submissions.
		// Doing a pre-check instead of letting constraint violation bubble up to the Controller - 
		// which would mark the Tx rollback only, and then proceeding with a merged save thereafter.
		List<Response> savedResponses = responseRepository.findAllByRespondantIdAndQuestionIdOrderByCreatedDesc(response.getRespondantId(), response.getQuestionId());
		
		if(savedResponses.isEmpty()) {
			savedResponse = responseRepository.save(response);
			log.debug("Saved response {}", response);
		} else {
			Response lastExistingResponse = savedResponses.iterator().next();
			lastExistingResponse.setResponseText(response.getResponseText());
			lastExistingResponse.setResponseMedia(response.getResponseMedia());
			lastExistingResponse.setResponseValue(response.getResponseValue());
			
			savedResponse = responseRepository.save(lastExistingResponse);
			log.debug("Duplicate response submission - merged results for {} with existing responseId {}", response, lastExistingResponse.getId());
		}

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
			@NonNull Integer type,
			@NonNull Timestamp fromDate,
			@NonNull Timestamp toDate) {

		return getBySearchParams(accountId,
				   statusLow, statusHigh,
				   locationId,
				   positionId,
				   type,
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
			@NonNull Integer type,
			@NonNull Timestamp fromDate,
			@NonNull Timestamp toDate,
			@NonNull @Min(value = 1) Integer pageNumber,
			@NonNull @Min(value = 1) @Max(value = 500) Integer pageSize
			) {

		Pageable  pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "id");

		Page<Respondant> respondants = null;

		if ((locationId != null) && (positionId != null)) {
			respondants = respondantRepository.findAllByAccountIdAndLocationIdAndPositionIdAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(accountId, locationId, positionId, type, statusLow, statusHigh, fromDate, toDate, pageRequest);
		} else if ((locationId == null) && (positionId == null)) {
			respondants = respondantRepository.findAllByAccountIdAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(accountId, type, statusLow, statusHigh, fromDate, toDate, pageRequest);
		} else if (locationId == null) {
			respondants = respondantRepository.findAllByAccountIdAndPositionIdAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(accountId, positionId, type, statusLow, statusHigh, fromDate, toDate, pageRequest);
		} else {
			respondants = respondantRepository.findAllByAccountIdAndLocationIdAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(accountId, locationId, type, statusLow, statusHigh, fromDate, toDate, pageRequest);
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
																										(g.getStatus() != Grader.STATUS_COMPLETED &&
																										g.getStatus() != Grader.STATUS_IGNORED))
																									.findAny();

															return !incompleteGrader.isPresent();
														})
														.collect(Collectors.toList());

		scoringEligibleRespondants.forEach(eligibleRespondant -> log.debug("Eligible respondant with all graders fulfilled: {}", eligibleRespondant.getId()));

		log.debug("Found {} respondants who are ungraded, with all graders fulfilled", scoringEligibleRespondants.size());
		return scoringEligibleRespondants;
	}

	@Override
	public Set<Response> getGradeableResponses(Long respondantId) {
		Respondant respondant = respondantRepository.findOne(respondantId);
		Set<Response> allresponses = respondant.getResponses();
		Set<SurveyQuestion> questionset= respondant.getAccountSurvey().getSurvey().getSurveyQuestions();
		Set<Response> gradeables = new HashSet<Response>();
		for (SurveyQuestion sq : questionset) {
			if (AUDIO_SCORING.equalsIgnoreCase(sq.getQuestion().getScoringModel())) {
			    Optional<Response> response = allresponses.stream().filter(resp -> sq.getQuestionId().equals(resp.getQuestionId())).findFirst();
			    if (response.isPresent()) {
			    	gradeables.add(response.get());
			    }
			}
		}
		log.debug("Returning {} gradeables for respondant {}", gradeables.size(), respondantId);
		return gradeables;
	}

	@Override
	public Outcome save(Outcome outcome) {
		return outcomeRepository.save(outcome);
	}

	@Override
	public Outcome addOutcomeToRespondant(Respondant respondant, Long targetId, Boolean value) {
		OutcomePK id = new OutcomePK();
		id.setRespondantId(respondant.getId());
		id.setPredictionTargetId(targetId);
		Outcome outcome = new Outcome();
		outcome.setId(id);
		outcome.setValue(value);
		return save(outcome);
	}

	@Override
	public Set<Outcome> getOutcomesForRespondant(Long respondantId) {
		return outcomeRepository.findAllByRespondantId(respondantId);
	}
	
	@Override
	public Set<Respondant> getByBenchmarkId(Long benchmarkId) {
		return respondantRepository.findAllByBenchmarkId(benchmarkId);
	}

	@Override
	public Set<Respondant> getCompletedForBenchmarkId(Long benchmarkId) {
		return respondantRepository.findAllByBenchmarkIdAndRespondantStatusGreaterThan(benchmarkId, Respondant.STATUS_COMPLETED-1);
	}
}
