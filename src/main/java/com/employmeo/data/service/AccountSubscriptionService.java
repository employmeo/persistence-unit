package com.employmeo.data.service;

import java.util.List;

import com.employmeo.data.model.Account;
import com.employmeo.data.model.AccountSubscription;

import lombok.NonNull;

public interface AccountSubscriptionService {

	List<AccountSubscription> getAllAccountSubscriptions();
	
	List<AccountSubscription> getAllAccountSubscriptionsForAccount(Long accountId);
	
	// List<AccountSubscription> getActiveAccountSubscriptionsForAccount(Long accountId);

	AccountSubscription getAccountSubscriptionById(@NonNull Long accountSubscriptionId);
	
	AccountSubscription save(@NonNull AccountSubscription accountSubscription);
	
	AccountSubscription setDefaultTrialPlan(@NonNull Account account);
	
}