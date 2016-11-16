package com.employmeo.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Location;

@Repository
public interface LocationRepository extends PagingAndSortingRepository<Location, Long> {
	
	@Query
	public Location findByAccountIdAndAtsId(Long accountId, String atsId);
	
}
