package com.employmeo.data.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.SubscriptionPlan;
import com.employmeo.data.repository.SubscriptionPlanRepository;

import jersey.repackaged.com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

	@Autowired
	private SubscriptionPlanRepository subscriptionPlanRepository;
	
	private static final String DEFAULT_TRIAL_PLAN_REFERENCE = "free_trial_v1";


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

	@Override
	public Range<Date> getSubscriptionTerm(@NonNull SubscriptionPlan subscriptionPlan) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		Date startDate = calendar.getTime();
		
		switch (subscriptionPlan.getTermUnit()) {
			case "D" : calendar.add(Calendar.DAY_OF_MONTH, subscriptionPlan.getTermValue()); break;
			case "M" : calendar.add(Calendar.MONTH, subscriptionPlan.getTermValue()); break;
			case "Y" : calendar.add(Calendar.YEAR, subscriptionPlan.getTermValue()); break;
			default : throw new IllegalArgumentException("Invalid subscriptionPlan configuration. SubscriptionTerm incorrectly setup: " + subscriptionPlan.getId());
		}
		Date endDate = calendar.getTime();
		
		Range<Date> subscriptionTerm = new Range<>(startDate, endDate);		
		return subscriptionTerm;
	}

	@Override
	public SubscriptionPlan getDefaultTrialPlan() throws IllegalStateException {
		SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findByReference(DEFAULT_TRIAL_PLAN_REFERENCE);
		log.debug("Found subscriptionPlan for reference {} as {}", DEFAULT_TRIAL_PLAN_REFERENCE, subscriptionPlan);
		
		if(null == subscriptionPlan) {
			throw new IllegalStateException("No default trial plan setup");
		}
		
		return subscriptionPlan;
	}

	
}
