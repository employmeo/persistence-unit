package com.employmeo.data.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.AccountSurvey;
import com.employmeo.data.model.Respondant;
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

	@Override
	public AccountSurvey getAccountSurveyByUuid(UUID asUuid) {
		AccountSurvey accountSurvey = accountSurveyRepository.findByAsUuid(asUuid);
		log.debug("Account Survey by uuid {} : {}", asUuid, accountSurvey);

		return accountSurvey;
	}


}
