package com.employmeo.data.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Range;

import com.employmeo.data.model.SubscriptionPlan;

import lombok.NonNull;

public interface SubscriptionPlanService {

	List<SubscriptionPlan> getAllSubscriptionPlans();

	SubscriptionPlan getSubscriptionPlanById(@NonNull Long subscriptionPlanId);
	
	SubscriptionPlan save(@NonNull SubscriptionPlan subscriptionPlan);
	
	void delete(Long subscriptionPlanId);

	Range<Date> getSubscriptionTerm(SubscriptionPlan subscriptionPlan);

	SubscriptionPlan getDefaultTrialPlan() throws IllegalStateException;

}