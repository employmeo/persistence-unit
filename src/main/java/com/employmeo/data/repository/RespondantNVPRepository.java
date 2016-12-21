package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.RespondantNVP;

@Repository
public interface RespondantNVPRepository extends PagingAndSortingRepository<RespondantNVP, Long> {
	
}
