package com.employmeo.data.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Outcome;

@Repository
public interface OutcomeRepository extends PagingAndSortingRepository<Outcome, Long> {
  
	@Query
	public Set<Outcome> findAllByRespondantId(Long respondantId);
	
}
