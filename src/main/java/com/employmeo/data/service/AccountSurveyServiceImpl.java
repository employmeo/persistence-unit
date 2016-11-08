package com.employmeo.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.*;
import com.employmeo.data.repository.*;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AccountSurveyServiceImpl implements AccountSurveyService {
	@Autowired
	private AccountSurveyRepository accountSurveyRepository;
	@Autowired
	private ResponseRepository responseRepository;
	@Autowired
	private RespondantRepository respondantRepository;
	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public AccountSurvey getAccountSurveyById(@NonNull Long accountSurveyId) {
		AccountSurvey accountSurvey = accountSurveyRepository.findOne(accountSurveyId);
		log.debug("Retrieved account survey for id {} as: {}", accountSurveyId, accountSurvey);

		return accountSurvey;
	}

	@Override
	public Response saveResponse(@NonNull Response response) {
		Response savedResponse = responseRepository.save(response);
		log.debug("Saved response {}", response);

		return savedResponse;
	}

	@Override
	public Response saveResponse(@NonNull Long respondantId, @NonNull Long questionId, Integer responseValue, String responseText) {
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
}
