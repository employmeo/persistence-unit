package com.employmeo.data.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Account;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
	
	@Query
	public Account findByAtsId(String atsId);
	
	@Query
	public Account findByAccountName(String accountName);
	
	@Query
	public Set<Account> findAllByAtsPartnerIdIn(List<Long> partnerIds);

}
