package com.employmeo.data.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.SubscriptionPlan;
import com.employmeo.data.model.Benchmark;
import com.employmeo.data.model.Location;
import com.employmeo.data.model.Position;
import com.employmeo.data.repository.SubscriptionPlanRepository;
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
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

	@Autowired
	private SubscriptionPlanRepository subscriptionPlanRepository;


	@Override
	public List<SubscriptionPlan> getAllSubscriptionPlans() {
		List<SubscriptionPlan> subscriptionPlans = Lists.newArrayList(subscriptionPlanRepository.findAll());
		log.debug("Retrieved all {} subscriptionPlans", subscriptionPlans);

		return subscriptionPlans;
	}

	@Override
	public SubscriptionPlan save(@NonNull SubscriptionPlan subscriptionPlan) {
		SubscriptionPlan savedSubscriptionPlan = subscriptionPlanRepository.save(subscriptionPlan);
		log.debug("Saved subscriptionPlan {}", savedSubscriptionPlan);

		return savedSubscriptionPlan;
	}

	@Override
	public SubscriptionPlan getSubscriptionPlanById(@NonNull Long subscriptionPlanId) {
		SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findOne(subscriptionPlanId);
		log.debug("Retrieved for id {} entity {}", subscriptionPlanId, subscriptionPlan);

		return subscriptionPlan;
	}

	@Override
	public void delete(Long subscriptionPlanId) {
		subscriptionPlanRepository.delete(subscriptionPlanId);
		log.debug("Deleted subscriptionPlan with id {}", subscriptionPlanId);
	}

	
}
