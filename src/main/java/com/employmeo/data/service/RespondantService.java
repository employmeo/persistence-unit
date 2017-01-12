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

	Respondant save(@NonNull Respondant respondant);

	Respondant getRespondantById(@NonNull Long respondantId);

	Page<Respondant> getByAccountId(@NonNull Long accountId, @NonNull Integer pageNumber, @NonNull Integer pageSize);

	Page<Respondant> getByAccountId(@NonNull Long accountId);

	Set<Respondant> getByBenchmarkId(@NonNull Long benchmarkId);
	
	Set<Respondant> getCompletedForBenchmarkId(@NonNull Long benchmarkId);
	
	Set<RespondantScore> getAllRespondantScores();

	RespondantScore save(@NonNull RespondantScore respondantScore);

	RespondantScore getRespondantScoreById(@NonNull RespondantScorePK respondantScorePK);

	Response saveResponse(Response response);

	Response saveResponse(Long respondantId, Long questionId, Integer responseValue, String responseText);

	Set<Response> getResponses(@NonNull UUID respondantUuid);

	List<Respondant> getAnalysisPendingRespondants();
	
	Set<Response> getGradeableResponses(@NonNull Long respondantId);

	Page<Respondant> getBySearchParams(
			@NonNull Long accountId,
			@NonNull Integer statusLow,
			@NonNull Integer statusHigh,
			Long locationId,
			Long positionId,
			@NonNull Integer type,
			@NonNull Timestamp fromDate,
			@NonNull Timestamp toDate);

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
			@NonNull @Min(value = 1) @Max(value = 100) Integer pageSize
			);

	public List<Respondant> getGraderBasedScoringPendingRespondants();
	
	public Outcome save(@NonNull Outcome outcome);
	
	public Outcome addOutcomeToRespondant(@NonNull Respondant respondant, @NonNull Long targetId, Boolean value);
	
	public Set<Outcome> getOutcomesForRespondant(@NonNull Long respondantId);
	
}