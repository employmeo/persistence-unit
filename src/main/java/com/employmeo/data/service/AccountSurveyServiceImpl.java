package com.employmeo.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.AccountSurvey;
import com.employmeo.data.repository.AccountSurveyRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AccountSurveyServiceImpl implements AccountSurveyService {
	@Autowired
	private AccountSurveyRepository accountSurveyRepository;

	@Override
	public AccountSurvey getAccountSurveyById(@NonNull Long accountSurveyId) {
		AccountSurvey accountSurvey = accountSurveyRepository.findOne(accountSurveyId);
		log.debug("Retrieved account survey for id {} as: {}", accountSurveyId, accountSurvey);

		return accountSurvey;
	}


}
