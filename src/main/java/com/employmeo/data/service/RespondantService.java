package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.*;

import lombok.NonNull;

public interface RespondantService {

	Set<Respondant> getAllRespondants();

	Respondant save(@NonNull Respondant respondant);

	Respondant getRespondantById(@NonNull Long respondantId);

	Set<RespondantScore> getAllRespondantScores();

	RespondantScore save(@NonNull RespondantScore respondantScore);

	RespondantScore getRespondantScoreById(@NonNull RespondantScorePK respondantScorePK);

}