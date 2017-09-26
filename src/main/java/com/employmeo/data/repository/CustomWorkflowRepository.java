package com.employmeo.data.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.CustomWorkflow;

@Repository
public interface CustomWorkflowRepository extends PagingAndSortingRepository<CustomWorkflow, Long> {
	
	List<CustomWorkflow> findByPositionId(Long positionId);

}
