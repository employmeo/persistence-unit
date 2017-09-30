package com.employmeo.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.NVPName;

@Repository
public interface NVPNameRepository extends PagingAndSortingRepository<NVPName, Long> {

	@Query
	NVPName findByName(String nvpName);
	
}