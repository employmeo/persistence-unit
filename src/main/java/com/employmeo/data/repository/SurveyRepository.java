package com.employmeo.data.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Survey;

@Repository
public interface SurveyRepository extends PagingAndSortingRepository<Survey, Long> {
	
	@Query
	Set<Survey> findByAvailabilityLessThan(Integer availability);
}
