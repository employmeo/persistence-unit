package com.employmeo.data.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.NVPName;

@Repository
public interface NVPNameRepository extends PagingAndSortingRepository<NVPName, Long> {

	@Query
	@Cacheable(value="nvpbyname", key = "#nvpName")
	NVPName findByName(String nvpName);
	
	@CacheEvict(value = "nvpbyname", key = "#nvpName")
	void resetCache(String name);
	
}