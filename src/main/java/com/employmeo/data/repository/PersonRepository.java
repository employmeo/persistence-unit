package com.employmeo.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Person;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

	@Query
	public Person findByAtsId(String atsId);
	
}
