package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Account;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

}
