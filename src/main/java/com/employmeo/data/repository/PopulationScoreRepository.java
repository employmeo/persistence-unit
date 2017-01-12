package com.employmeo.data.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.PopulationScore;

@Repository
public interface PopulationScoreRepository extends PagingAndSortingRepository<PopulationScore, Long> {
  
	@Query
	public Set<PopulationScore> findAllByPopulationId(Long populationId);
	
}
