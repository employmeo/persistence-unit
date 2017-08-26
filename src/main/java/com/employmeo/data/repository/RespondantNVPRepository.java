package com.employmeo.data.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.RespondantNVP;

@Repository
public interface RespondantNVPRepository extends PagingAndSortingRepository<RespondantNVP, Long> {

	@Query
	Set<RespondantNVP> findAllByRespondantId(Long respondantId);

	@Query
	Set<RespondantNVP> findAllByRespondantIdAndUseInModel(Long respondantId, Boolean useInModel);
	
	@Query
	Set<RespondantNVP> findAllByRespondantIdAndShowInPortal(Long respondantId, Boolean showInPortal);	
}
