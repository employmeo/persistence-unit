package com.employmeo.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	@Query
	User findByEmail(String email);

	@Query
	User findByEmailAndPassword(String email, String password);
		
}
