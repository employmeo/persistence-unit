package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.CorefactorDescription;

@Repository
public interface CorefactorDescriptionRepository extends PagingAndSortingRepository<CorefactorDescription, Long> {

}
