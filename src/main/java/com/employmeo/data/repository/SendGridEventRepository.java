package com.employmeo.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.SendGridEmailEvent;

@Repository
public interface SendGridEventRepository extends PagingAndSortingRepository<SendGridEmailEvent, Long> {
	
	@Query
	public List<SendGridEmailEvent> findAllByPersonIdOrderByTimeStamp(Long personId);

}