package com.employmeo.data.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.employmeo.data.model.*;

import lombok.NonNull;

public interface RespondantService {

	Respondant getRespondant(@NonNull UUID respondantUuid);

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

}