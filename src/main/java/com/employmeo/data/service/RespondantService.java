package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.*;

import lombok.NonNull;

public interface RespondantService {

	Set<Respondant> getAllRespondants();

	Respondant save(Respondant respondant);

	Respondant getRespondantById(Long respondantId);

	Set<RespondantScore> getAllRespondantScores();

	RespondantScore save(RespondantScore respondantScore);

	RespondantScore getRespondantScoreById(RespondantScorePK respondantScorePK);

}