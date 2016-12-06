package com.employmeo.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Criterion;
import com.employmeo.data.model.RespondantNVP;

@Repository
public interface RespondantNVPRepository extends PagingAndSortingRepository<RespondantNVP, Long> {
	
}
