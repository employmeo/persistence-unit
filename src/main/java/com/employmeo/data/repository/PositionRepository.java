package com.employmeo.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Location;
import com.employmeo.data.model.Position;

@Repository
public interface PositionRepository extends PagingAndSortingRepository<Position, Long> {

	@Query
	public Position findByAccountIdAndAtsId(Long accountId, String atsId);
	
	@Query
	public List<Position> findAllByAccountIdAndStatusIn(Long accountId, List<Integer> statuses);
	
}
