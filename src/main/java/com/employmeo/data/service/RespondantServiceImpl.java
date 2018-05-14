package com.employmeo.data.service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.*;
import com.employmeo.data.repository.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
	private RespondantNVPRepository respondantNVPRepository;
	@Autowired
	private NVPNameRepository nvpNameRepository;
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
	private static final String[] GRADEABLES = {"audio","audio+","video","video+"}; // audio- and video- not graded
	private static final Integer AUDIOTYPE = 16;
	private static final Integer VIDEOTYPE = 28;
	
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
		
		List<Respondant> rs = respondantRepository.findAllByAccountSurveyIdAndPayrollIdOrderByRespondantStatusDescCreatedDateDesc(accountSurveyId, payrollId);
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
		//Optional<Respondant> respondant = respondantRepository.findById(respondantId);
		//if (respondant.isPresent()){
		//	log.debug("Retrieved for id {} entity {}", respondantId, respondant);
		//	return respondant.get();
		//}
		//return null;
		return respondantRepository.findOne(respondantId);
	}

	@Override
	public Page<Respondant> getByAccountId(Long accountId, @NonNull @Min(value = 1) Integer pageNumber, @NonNull @Min(value = 1) @Max(value = 100) Integer pageSize) {
		//Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.DESC, "id");
		Pageable pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "id");

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
		//Optional<RespondantScore> respondantScore = respondantScoreRepository.findById(respondantScorePK);
		//if(respondantScore.isPresent()) {
		//	log.debug("Retrieved for id {} entity {}", respondantScorePK, respondantScore);
		//	return respondantScore.get();
		//}
		//return null;
		
		return respondantScoreRepository.findOne(respondantScorePK);
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
		//Respondant respondant = respondantRepository.findById(respondantId).get();
		//Question question = questionRepository.findById(questionId).get();
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
		return getResponsesById(respondant.getId());
	}

	@Override
	public Set<Response> getResponsesById(@NonNull Long respondantId) {
		return responseRepository.findAllByRespondantId(respondantId);
	}
	
	@Override
	public Page<Respondant> getBySearchParams(
			@NonNull Long accountId,
			@NonNull Integer statusLow,
			@NonNull Integer statusHigh,
			List<Long> locationIds,
			Long positionId,
			@NonNull Integer type,
			@NonNull Timestamp fromDate,
			@NonNull Timestamp toDate) {

		return getBySearchParams(accountId,
				   statusLow, statusHigh,
				   locationIds,
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
			List<Long> locationIds,
			Long positionId,
			@NonNull Integer type,
			@NonNull Timestamp fromDate,
			@NonNull Timestamp toDate,
			@NonNull @Min(value = 1) Integer pageNumber,
			@NonNull @Min(value = 1) @Max(value = 500) Integer pageSize
			) {

		//Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.DESC, "id");
		Pageable pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "id");

		Page<Respondant> respondants = null;

		if ((!locationIds.isEmpty()) && (positionId != null)) {
			respondants = respondantRepository.findAllByAccountIdAndLocationIdInAndPositionIdAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(accountId, locationIds, positionId, type, statusLow, statusHigh, fromDate, toDate, pageRequest);
		} else if ((locationIds.isEmpty()) && (positionId == null)) {
			respondants = respondantRepository.findAllByAccountIdAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(accountId, type, statusLow, statusHigh, fromDate, toDate, pageRequest);
		} else if (locationIds.isEmpty()) {
			respondants = respondantRepository.findAllByAccountIdAndPositionIdAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(accountId, positionId, type, statusLow, statusHigh, fromDate, toDate, pageRequest);
		} else {
			respondants = respondantRepository.findAllByAccountIdAndLocationIdInAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(accountId, locationIds, type, statusLow, statusHigh, fromDate, toDate, pageRequest);
		}

	    return respondants;
	};
	
	
	@Override
	public List<Respondant> getAnalysisPendingRespondants() {
		List<Integer> scoringEligibleRespondantStatuses = Arrays.asList(Respondant.STATUS_COMPLETED, Respondant.STATUS_ADVCOMPLETED);
		List<Respondant> scoringEligibleRespondants = respondantRepository.findAllByRespondantStatusInOrderByFinishTimeDesc(scoringEligibleRespondantStatuses);

		log.debug("Returning {} scoring eligible respondants", scoringEligibleRespondants.size());
		return scoringEligibleRespondants;
	}
	
	@Override
	public List<Respondant> getScoredApplicantsByPosition(Long positionId) {
		return respondantRepository.findAllByPositionIdAndTypeAndRespondantStatusGreaterThan(positionId, Respondant.TYPE_APPLICANT, Respondant.STATUS_SCORED);
	}
	
	@Override
	public Page<Respondant> getErrorStatusRespondants(Long accountId, List<Integer> statuses, Boolean errorStatus, Integer pageNumber) {

		//Pageable pageRequest = PageRequest.of(pageNumber - 1, DEFAULT_PAGE_SIZE, Sort.Direction.DESC, "id");
		Pageable pageRequest = new PageRequest(pageNumber - 1, DEFAULT_PAGE_SIZE, Sort.Direction.DESC, "id");
		Page<Respondant> respondants = null;

		if ((accountId != null) && (!statuses.isEmpty())) {
			respondants = respondantRepository.findAllByAccountIdAndRespondantStatusInAndErrorStatus(accountId, statuses, errorStatus, pageRequest);
		} else if (!statuses.isEmpty()) {
			respondants = respondantRepository.findAllByRespondantStatusInAndErrorStatus(statuses, errorStatus, pageRequest);
		} else if (accountId != null) {
			respondants = respondantRepository.findAllByAccountIdAndErrorStatus(accountId, errorStatus, pageRequest);
		} else {
			respondants = respondantRepository.findAllByErrorStatus(errorStatus, pageRequest);
		}

	    return respondants;
	};
	

	@Override
	public Set<Respondant> getByBenchmarkId(Long benchmarkId) {
		return respondantRepository.findAllByBenchmarkId(benchmarkId);
	}

	@Override
	public Set<Respondant> getCompletedForBenchmarkId(Long benchmarkId) {
		return respondantRepository.findAllByBenchmarkIdAndRespondantStatusGreaterThan(benchmarkId, Respondant.STATUS_COMPLETED-1);
	}

	@Override
	public Respondant getRespondantByPersonAndPosition(Person person, Position position) {		
		return respondantRepository.findByPersonIdAndPositionId(person.getId(), position.getId());
	}
	
	@Override
	public List<Respondant> getAllRespondantsByStatus(Integer status) {
		List<Integer> statuses = Arrays.asList(status);
		List<Respondant> respondants = respondantRepository.findAllByRespondantStatusInOrderByFinishTimeDesc(statuses);		
		log.debug("Returning {} respondants for status {}", respondants.size(), status);
		return respondants;
	}
	
	@Override
	public List<Respondant> getGraderBasedScoringPendingRespondants() {
		List<Integer> ungradedRespondantStatuses = Arrays.asList(Respondant.STATUS_UNGRADED, Respondant.STATUS_ADVUNGRADED);
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
	public List<Respondant> getPredictionPendingRespondants() {
		// TODO Enable predictions for stage two.
		List<Integer> predictionNeededStatuses = Arrays.asList(Respondant.STATUS_SCORED, Respondant.STATUS_ADVSCORESADDED);
		List<Respondant> predictionNeededRespondants = respondantRepository.findAllByRespondantStatusInOrderByFinishTimeDesc(predictionNeededStatuses);
		log.debug("Found {} respondants in needs prediction status", predictionNeededRespondants.size());
		return predictionNeededRespondants;
	}

	@Override
	public Set<Response> getGradeableResponses(Long respondantId) {
		//Respondant respondant = respondantRepository.findById(respondantId).get();
		Respondant respondant = respondantRepository.findOne(respondantId);
		Set<Response> allresponses = responseRepository.findAllByRespondantId(respondant.getId());
		Set<SurveyQuestion> questionset= respondant.getAccountSurvey().getSurvey().getSurveyQuestions();
		Set<Response> gradeables = new HashSet<Response>();
		for (SurveyQuestion sq : questionset) {
			if (Arrays.asList(GRADEABLES).contains(sq.getQuestion().getScoringModel().toLowerCase())) {
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
	public Set<Response> getAudioResponses(Long respondantId) {
		//Respondant respondant = respondantRepository.findById(respondantId).get();
		Respondant respondant = respondantRepository.findOne(respondantId);
		Set<Response> allresponses = responseRepository.findAllByRespondantId(respondant.getId());
		Set<SurveyQuestion> questionset= respondant.getAccountSurvey().getSurvey().getSurveyQuestions();
		Set<Response> audioresponses = new HashSet<Response>();
		for (SurveyQuestion sq : questionset) {
			if (sq.getQuestion().getQuestionType() == AUDIOTYPE) {
			    Optional<Response> response = allresponses.stream().filter(resp -> sq.getQuestionId().equals(resp.getQuestionId())).findFirst();
			    if (response.isPresent()) {
			    	audioresponses.add(response.get());
			    }
			}
		}
		log.debug("Returning {} audioresponses for respondant {}", audioresponses.size(), respondantId);
		return audioresponses;
	}
	
	public Set<Response> getResponsesToQuestions(Long respondantId, List<SurveyQuestion> questions) {
		List<Long> questionIds = questions.stream().map(SurveyQuestion::getQuestionId).collect(Collectors.toList()); 
		return responseRepository.findByRespondantIdAndQuestionIdIn(respondantId, questionIds);
	}	
	
	@Override
	public Set<Response> getVideoResponses(Long respondantId) {
		//Respondant respondant = respondantRepository.findById(respondantId).get();
		Respondant respondant = respondantRepository.findOne(respondantId);
		Set<Response> allresponses = responseRepository.findAllByRespondantId(respondant.getId());
		Set<SurveyQuestion> questionset= respondant.getAccountSurvey().getSurvey().getSurveyQuestions();
		Set<Response> videoresponses = new HashSet<Response>();
		for (SurveyQuestion sq : questionset) {
			if (sq.getQuestion().getQuestionType() == VIDEOTYPE) {
			    Optional<Response> response = allresponses.stream().filter(resp -> sq.getQuestionId().equals(resp.getQuestionId())).findFirst();
			    if (response.isPresent()) {
			    	videoresponses.add(response.get());
			    }
			}
		}
		log.debug("Returning {} videoresponses for respondant {}", videoresponses.size(), respondantId);
		return videoresponses;
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
	public RespondantNVP save(RespondantNVP nvp) {		
		if (null == nvp.getNameId()) nvp.setNameId(getNVPNameId(nvp.getName()));
		return respondantNVPRepository.save(nvp);
	}
	
	@Cacheable(value = "nvpidbyname")
	private Long getNVPNameId(String name) {
		NVPName nameId = nvpNameRepository.findByName(name);
		if (null == nameId) {	
			NVPName newNameId = new NVPName();
			newNameId.setName(name);
			nameId = nvpNameRepository.save(newNameId);
			log.info("New NVP ID#{} added for: {}",nameId.getId(),nameId.getName());
		}
		return nameId.getId(); 
	}
	
	@Override
	public Iterable<RespondantNVP> save(Iterable<RespondantNVP> nvps) {
		nvps.forEach(nvp -> {
			if (null == nvp.getNameId()) nvp.setNameId(getNVPNameId(nvp.getName()));
			});
		//return respondantNVPRepository.saveAll(nvps);
		return respondantNVPRepository.save(nvps);
	}

	@Override
	public RespondantNVP addNVPToRespondant(Respondant respondant, String name, String value) {
		return addNVPToRespondant(respondant, name, value, false, true);
	}

	@Override
	public RespondantNVP addNVPToRespondant(Respondant respondant, String name, String value, Boolean display, Boolean inModel) {
		RespondantNVP nvp = new RespondantNVP();
		nvp.setName(name);
		nvp.setValue(value);
		nvp.setRespondantId(respondant.getId());
		nvp.setNameId(getNVPNameId(name));
		nvp.setUseInModel(inModel);
		nvp.setShowInPortal(display);
		return respondantNVPRepository.save(nvp);
	}
	
	@Override
	public Set<RespondantNVP> getNVPsForRespondant(Long respondantId) {
		return respondantNVPRepository.findAllByRespondantId(respondantId);
	}

	@Override
	public Set<RespondantNVP> getModelNVPsForRespondant(Long respondantId) {
		return respondantNVPRepository.findAllByRespondantIdAndUseInModel(respondantId, true);
	}

	@Override
	public Set<RespondantNVP> getDisplayNVPsForRespondant(Long respondantId) {
		return respondantNVPRepository.findAllByRespondantIdAndShowInPortal(respondantId, true);
	}
	
	@Override
	public void markError(Respondant respondant) {
		respondantRepository.setErrorStatusById(true, respondant.getId());
	}
	
	@Override
	public void clearError(Long respondantId) {
		respondantRepository.setErrorStatusById(false, respondantId);	
	}

	@Override
	public void clearErrors(List<Long> respondantIds) {
		respondantRepository.setErrorStatusByIds(false, respondantIds);	
	}

	@Override
	public boolean isGraderMinMet(Respondant respondant) {
		ReferenceCheckConfig rcConfig = respondant.getAccountSurvey().getRcConfig();
		if (rcConfig == null) return true;
		Integer minGraders = rcConfig.getMinReferences();
		if ((minGraders == null) || (minGraders <= 0)) return true;
		log.debug("Respondant {} needs {} graders",respondant.getId(),minGraders);
		if (respondant.getWaveGraderMin()) return true;
		log.debug("Respondant {} minimum grader requirement not waved",respondant.getId(),minGraders);
		List<Grader> graders = graderRepository.findAllByRespondantId(respondant.getId());
		Integer completed = 0;
		for (Grader grader : graders) if (grader.getStatus() == Grader.STATUS_COMPLETED) completed++;
		
		return (completed >= minGraders);
	}

	@Override
	public List<String> getWarningMessages(Respondant respondant) {
		List<Grader> graders = graderRepository.findAllByRespondantId(respondant.getId());
		List<String> emails = Lists.newArrayList();
		List<String> ipAddresses = Lists.newArrayList();
		boolean dupGrader = false;
		boolean dupResp = false;
		for (Grader grader : graders) {
			if (grader.getType() != Grader.TYPE_PERSON) continue;
			String ipAddress = grader.getIpAddress();
			if (ipAddress != null) {
				if (ipAddress.equalsIgnoreCase(respondant.getIpAddress())) dupResp = true;
				for (String check : ipAddresses) if (check.equalsIgnoreCase(ipAddress)) dupGrader = true;
			}

			String email = grader.getPerson().getEmail();
			if (email.equalsIgnoreCase(respondant.getPerson().getEmail())) dupResp = true;
			for (String check : emails) if (check.equalsIgnoreCase(email)) dupGrader = true;
			
			if (dupGrader || dupResp) break;
			emails.add(email);
			if (ipAddress != null) ipAddresses.add(ipAddress);
		}
				
		List<String> messages = Lists.newArrayList();
		if (dupGrader) messages.add("Some references share the same email or IP address.");
		if (dupResp) messages.add("Candidate email or IP address matches a reference.");
		return messages;
	}

	@Override
	public Response getResponseById(Long responseId) {
		//Optional<Response> response = responseRepository.findById(responseId);
		//if (response.isPresent()) return response.get();
		//return null;
		return responseRepository.findOne(responseId);
	}

	@Override
	public Iterable<RespondantScore> saveAll(Iterable<RespondantScore> respondantScores) {
		//return respondantScoreRepository.saveAll(respondantScores);
		return respondantScoreRepository.save(respondantScores);
	}

	
}
