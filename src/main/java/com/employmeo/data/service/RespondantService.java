package com.employmeo.data.service;

import java.sql.Timestamp;
import java.util.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.data.domain.Page;

import com.employmeo.data.model.*;

import lombok.NonNull;

public interface RespondantService {

	Respondant getRespondant(@NonNull UUID respondantUuid);

	Respondant getRespondantByAccountSurveyIdAndPayrollId(@NonNull Long accountSurveyId, @NonNull String payrollId);

	Respondant getRespondantByAtsId(@NonNull String atsId);

	Respondant getRespondantByAccountIdAndAtsId(@NonNull Long accountId, @NonNull String atsId);

	Respondant getRespondantById(@NonNull Long respondantId);

	Respondant getRespondantByPersonAndPosition(Person person, Position position);

	Respondant save(@NonNull Respondant respondant);
	
	Set<Respondant> getByBenchmarkId(@NonNull Long benchmarkId);
	
	Set<Respondant> getCompletedForBenchmarkId(@NonNull Long benchmarkId);
	
	List<Respondant> getAnalysisPendingRespondants();

	List<Respondant> getGraderBasedScoringPendingRespondants();
	
	List<Respondant> getAllRespondantsByStatus(@NonNull Integer status);
	
	Page<Respondant> getByAccountId(@NonNull Long accountId, @NonNull Integer pageNumber, @NonNull Integer pageSize);

	Page<Respondant> getByAccountId(@NonNull Long accountId);

	Page<Respondant> getBySearchParams(
			@NonNull Long accountId,
			@NonNull Integer statusLow,
			@NonNull Integer statusHigh,
			List<Long> locationIds,
			Long positionId,
			@NonNull Integer type,
			@NonNull Timestamp fromDate,
			@NonNull Timestamp toDate);
	
	Page<Respondant> getBySearchParams(Long accountId, Integer statusLow, Integer statusHigh, List<Long> locationIds,
			Long positionId, Integer type, Timestamp fromDate, Timestamp toDate, Integer pageNumber, Integer pageSize);
	
	RespondantScore getRespondantScoreById(@NonNull RespondantScorePK respondantScorePK);
	
	RespondantScore save(@NonNull RespondantScore respondantScore);

	Set<RespondantScore> getAllRespondantScores();

	Response saveResponse(Response response);

	Response saveResponse(Long respondantId, Long questionId, Integer responseValue, String responseText);

	Set<Response> getResponses(@NonNull UUID respondantUuid);
	
	Set<Response> getGradeableResponses(@NonNull Long respondantId);
	
	Set<Response> getAudioResponses(Long respondantId);
	
	Set<Response> getVideoResponses(Long respondantId);

	Outcome save(@NonNull Outcome outcome);
	
	Outcome addOutcomeToRespondant(@NonNull Respondant respondant, @NonNull Long targetId, Boolean value);
	
	Set<Outcome> getOutcomesForRespondant(@NonNull Long respondantId);

	RespondantNVP save(@NonNull RespondantNVP nvp);
	
	Iterable<RespondantNVP> save(@NonNull Iterable<RespondantNVP> nvps);
	
	RespondantNVP addNVPToRespondant(@NonNull Respondant respondant, @NonNull String name, String value);
	
	RespondantNVP addNVPToRespondant(Respondant respondant, String name, String value, Boolean display, Boolean inModel);
	
	Set<RespondantNVP> getNVPsForRespondant(@NonNull Long respondantId);
	
	Set<RespondantNVP> getModelNVPsForRespondant(@NonNull Long respondantId);
	
	Set<RespondantNVP> getDisplayNVPsForRespondant(@NonNull Long respondantId);

	List<Respondant> getPredictionPendingRespondants();

	void markError(Respondant respondant);

	boolean isGraderMinMet(Respondant respondant);
}