package com.employmeo.data.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	@Query
	@Deprecated
	User findByEmail(String email);

	@Query
	User findByEmailAndPassword(String email, String password);
	
	@Query
	User findByEmailIgnoreCase(String email);

	@Query
	User findByEmailIgnoreCaseAndPassword(String email, String password);
	
	@Query
	Set<User> findAllByUserAccountId(Long userAccountId);
		
}
