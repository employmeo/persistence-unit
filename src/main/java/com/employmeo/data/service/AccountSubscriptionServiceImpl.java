package com.employmeo.data.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Account;
import com.employmeo.data.model.AccountSubscription;
import com.employmeo.data.model.PlanStatus;
import com.employmeo.data.model.SubscriptionPlan;
import com.employmeo.data.repository.AccountSubscriptionRepository;

import jersey.repackaged.com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AccountSubscriptionServiceImpl implements AccountSubscriptionService {

	@Autowired
	private AccountSubscriptionRepository accountSubscriptionRepository;
	
	@Autowired
	private SubscriptionPlanService subscriptionPlanService;


	@Override
	public List<AccountSubscription> getAllAccountSubscriptions() {
		List<AccountSubscription> accountSubscriptions = Lists.newArrayList(accountSubscriptionRepository.findAll());
		log.debug("Retrieved all {} accountSubscriptions", accountSubscriptions);

		return accountSubscriptions;
	}

	@Override
	public AccountSubscription save(@NonNull AccountSubscription accountSubscription) {
		AccountSubscription savedAccountSubscription = accountSubscriptionRepository.save(accountSubscription);
		log.debug("Saved accountSubscription {}", savedAccountSubscription);

		return savedAccountSubscription;
	}

	@Override
	public AccountSubscription getAccountSubscriptionById(@NonNull Long accountSubscriptionId) {
		AccountSubscription accountSubscription = accountSubscriptionRepository.findOne(accountSubscriptionId);
		log.debug("Retrieved for id {} entity {}", accountSubscriptionId, accountSubscription);

		return accountSubscription;
	}

	@Override
	public List<AccountSubscription> getAllAccountSubscriptionsForAccount(Long accountId) {
		List<AccountSubscription> accountSubscriptions = Lists.newArrayList(accountSubscriptionRepository.findByAccountId(accountId));
		log.debug("Retrieved for accountId {} accountSubscriptions {}", accountId, accountSubscriptions);

		return accountSubscriptions;
	}

	@Override
	public AccountSubscription setDefaultTrialPlan(@NonNull Account account) {
		SubscriptionPlan defaultTrialPlan = subscriptionPlanService.getDefaultTrialPlan();		
		AccountSubscription accountSubscription = createAccountSubscription(account, defaultTrialPlan);
		
		return accountSubscription;
	}

	private AccountSubscription createAccountSubscription(@NonNull Account account, @NonNull SubscriptionPlan subscriptionPlan) {
		Range<Date> subscriptionTerm = subscriptionPlanService.getSubscriptionTerm(subscriptionPlan);
		
		AccountSubscription accountSubscription = new AccountSubscription();
		accountSubscription.setAccountId(account.getId());
		accountSubscription.setPlanId(subscriptionPlan.getId());
		if(null != subscriptionTerm) {
			accountSubscription.setStartDate(subscriptionTerm.getLowerBound());
			accountSubscription.setEndDate(subscriptionTerm.getUpperBound());
		}
		accountSubscription.setAllowableAssessments(subscriptionPlan.getAllowableAssessments());
		accountSubscription.setAllowableBenchmarks(subscriptionPlan.getAllowableBenchmarks());
		accountSubscription.setScoredRespondantsMonthlyLimit(subscriptionPlan.getScoredRespondantsMonthlyLimit());
		accountSubscription.setScoredRespondantsTotalLimit(subscriptionPlan.getScoredRespondantsTotalLimit());
		accountSubscription.setPlanStatus(PlanStatus.EFFECTIVE);
		accountSubscription.setActive(true);
		accountSubscription.setCreatedDate(new Date());
		
		AccountSubscription savedAccountSubscription = accountSubscriptionRepository.save(accountSubscription);
		log.debug("Saved new accountSubscription {}", savedAccountSubscription);
		
		return savedAccountSubscription;
	}


}
