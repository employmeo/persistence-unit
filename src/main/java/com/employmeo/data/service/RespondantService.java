package com.employmeo.data.service;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

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

	Set<RespondantScore> getAllRespondantScores();

	RespondantScore save(@NonNull RespondantScore respondantScore);

	RespondantScore getRespondantScoreById(@NonNull RespondantScorePK respondantScorePK);

	Response saveResponse(Response response);

	Response saveResponse(Long respondantId, Long questionId, Integer responseValue, String responseText);

	Set<Response> getResponses(@NonNull UUID respondantUuid);
	
	Page<Respondant> getBySearchParams(
			@NonNull Long accountId,
			@NonNull Integer statusLow,
			@NonNull Integer statusHigh,
			Long locationId,
			Long positionId,
			@NonNull Timestamp fromDate,
			@NonNull Timestamp toDate);
	
	public Page<Respondant> getBySearchParams(
			@NonNull Long accountId,
			@NonNull Integer statusLow,
			@NonNull Integer statusHigh,
			Long locationId,
			Long positionId,
			@NonNull Timestamp fromDate,
			@NonNull Timestamp toDate,
			@NonNull @Min(value = 1) Integer pageNumber, 
			@NonNull @Min(value = 1) @Max(value = 100) Integer pageSize
			);
}