package com.employmeo.data.service;

import java.util.List;

import com.employmeo.data.model.SubscriptionPlan;

import lombok.NonNull;

public interface SubscriptionPlanService {

	List<SubscriptionPlan> getAllSubscriptionPlans();

	SubscriptionPlan getSubscriptionPlanById(@NonNull Long subscriptionPlanId);
	
	SubscriptionPlan save(@NonNull SubscriptionPlan subscriptionPlan);
	
	void delete(Long subscriptionPlanId);

}