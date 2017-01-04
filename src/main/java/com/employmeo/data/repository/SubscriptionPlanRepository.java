package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.SubscriptionPlan;

@Repository
public interface SubscriptionPlanRepository extends PagingAndSortingRepository<SubscriptionPlan, Long> {

}