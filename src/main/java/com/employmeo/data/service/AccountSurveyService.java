package com.employmeo.data.service;

import java.util.UUID;

import com.employmeo.data.model.AccountSurvey;

public interface AccountSurveyService {

	AccountSurvey getAccountSurveyById(Long accountSurveyId);
	
	AccountSurvey getAccountSurveyByUuid(UUID asUuid);

}