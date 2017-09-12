package com.employmeo.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Location;

@Repository
public interface LocationRepository extends PagingAndSortingRepository<Location, Long> {
	
	@Query
	public Location findByAccountIdAndAtsId(Long accountId, String atsId);
	
	@Query
	public List<Location> findAllByAccountIdAndStatusInAndTypeIn(Long accountId, List<Integer> statuses, List<Integer> types);
	
	@Query
	public List<Location> findAllByParentId(Long locationId);

}
