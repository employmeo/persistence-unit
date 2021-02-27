package com.employmeo.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.SendGridEmailEvent;

@Repository
public interface SendGridEventRepository extends PagingAndSortingRepository<SendGridEmailEvent, Long> {
	
	@Query
	public List<SendGridEmailEvent> findAllByPersonIdOrderByTimeStampDesc(Long personId);

	@Query
	public List<SendGridEmailEvent> findAllByEmailOrderByTimeStampDesc(String email);

	@Query
	public List<SendGridEmailEvent> findAllByEmailIgnoreCaseOrderByTimeStampDesc(String email);

}