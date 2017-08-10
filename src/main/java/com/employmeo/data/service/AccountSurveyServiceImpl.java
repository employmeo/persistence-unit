package com.employmeo.data.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.AccountSurvey;
import com.employmeo.data.model.GraderConfig;
import com.employmeo.data.repository.AccountSurveyRepository;
import com.employmeo.data.repository.GraderConfigRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AccountSurveyServiceImpl implements AccountSurveyService {
	@Autowired
	private AccountSurveyRepository accountSurveyRepository;
	
	@Autowired
	private GraderConfigRepository graderConfigRepository;

	@Override
	@Cacheable(value="asurveybyid")
	public AccountSurvey getAccountSurveyById(@NonNull Long accountSurveyId) {
		AccountSurvey accountSurvey = accountSurveyRepository.findOne(accountSurveyId);
		log.debug("Retrieved account survey for id {} as: {}", accountSurveyId, accountSurvey);

		return accountSurvey;
	}

	@Override
	@Cacheable(value="asurveybyuuid")
	public AccountSurvey getAccountSurveyByUuid(UUID asUuid) {
		AccountSurvey accountSurvey = accountSurveyRepository.findByUuId(asUuid);
		log.debug("Account Survey by uuid {} : {}", asUuid, accountSurvey);
		return accountSurvey;
	}

	@Override
	public Set<GraderConfig> getGraderConfigsForSurvey(Long asId) {
		return graderConfigRepository.findAllByAsid(asId);
	}

	@Override
	public AccountSurvey save(AccountSurvey accountSurvey) {
		log.debug("saving Account Survey {}", accountSurvey);
		return accountSurveyRepository.save(accountSurvey);
	}

}
