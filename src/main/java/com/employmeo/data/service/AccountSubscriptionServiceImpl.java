package com.employmeo.data.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.AccountSubscription;
import com.employmeo.data.model.Benchmark;
import com.employmeo.data.model.Location;
import com.employmeo.data.model.Position;
import com.employmeo.data.repository.AccountSubscriptionRepository;
import com.employmeo.data.repository.BenchmarkRepository;
import com.employmeo.data.repository.LocationRepository;
import com.employmeo.data.repository.PositionRepository;

import jersey.repackaged.com.google.common.collect.Lists;
import jersey.repackaged.com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AccountSubscriptionServiceImpl implements AccountSubscriptionService {

	@Autowired
	private AccountSubscriptionRepository accountSubscriptionRepository;


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


}
