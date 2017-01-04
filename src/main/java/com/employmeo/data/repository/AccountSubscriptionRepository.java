package com.employmeo.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.AccountSubscription;

@Repository
public interface AccountSubscriptionRepository extends PagingAndSortingRepository<AccountSubscription, Long> {

	@Query
	AccountSubscription findByAccountId(Long accountId);

}
