package com.employmeo.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Position;

@Repository
public interface PositionRepository extends PagingAndSortingRepository<Position, Long> {

	@Query
	public Position findByAccountIdAndAtsId(Long accountId, String atsId);
	
}
