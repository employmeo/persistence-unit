package com.employmeo.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Grade;

@Repository
public interface GradeRepository extends PagingAndSortingRepository<Grade, Long> {
	
	@Query
	public List<Grade> findAllByGraderId(Long graderId);
}
