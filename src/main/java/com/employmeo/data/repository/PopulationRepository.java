package com.employmeo.data.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Population;

@Repository
public interface PopulationRepository extends PagingAndSortingRepository<Population, Long> {
  
	@Query
	public Set<Population> findAllByBenchmarkId(Long benchmarkId);
	
}
