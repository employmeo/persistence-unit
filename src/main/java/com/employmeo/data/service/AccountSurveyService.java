package com.employmeo.data.service;

import com.employmeo.data.model.AccountSurvey;
import com.employmeo.data.model.Response;

import lombok.NonNull;

public interface AccountSurveyService {

	AccountSurvey getAccountSurveyById(Long accountSurveyId);

	Response saveResponse(Response response);

	Response saveResponse(Long respondantId, Long questionId, Integer responseValue, String responseText);

}