package com.employmeo.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Corefactor;

@Repository
public interface CorefactorRepository extends PagingAndSortingRepository<Corefactor, Long> {

	@Query
	Corefactor findByForeignId(String foreignId);


}
