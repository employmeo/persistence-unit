package com.employmeo.data.repository;

import java.util.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.GraderConfig;

@Repository
public interface GraderConfigRepository extends PagingAndSortingRepository<GraderConfig, Long> {

	@Query
	public Set<GraderConfig> findAllByUserId(Long userId);
	
	@Query
	public Set<GraderConfig> findAllByAsid(Long asId);

}
