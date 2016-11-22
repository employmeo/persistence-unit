package com.employmeo.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Partner;

@Repository
public interface PartnerRepository extends PagingAndSortingRepository<Partner, Long> {
	
	@Query
	Partner findByPartnerName(String partnerName);

	@Query
	Partner findByLogin(String login);

}
