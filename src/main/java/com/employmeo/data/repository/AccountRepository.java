package com.employmeo.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Account;

import lombok.NonNull;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
	
	@Query
	public Account findByAtsId(String atsId);

}
