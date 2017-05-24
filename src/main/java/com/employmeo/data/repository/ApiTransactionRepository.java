package com.employmeo.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.ApiTransaction;

@Repository
public interface ApiTransactionRepository extends PagingAndSortingRepository<ApiTransaction, Long> {
  
	@Query
	public ApiTransaction findFirstByApiNameAndObjectIdOrderByCreatedDateDesc(String apiName, Long objectId);
	
}
