package com.employmeo.data.service;

import java.util.Set;
import java.util.UUID;

import com.employmeo.data.model.AccountSurvey;
import com.employmeo.data.model.GraderConfig;

public interface AccountSurveyService {

	AccountSurvey getAccountSurveyById(Long accountSurveyId);
	
	AccountSurvey getAccountSurveyByUuid(UUID asUuid);
	
	Set<GraderConfig> getGraderConfigsForSurvey(Long asId);

}