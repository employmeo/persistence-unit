package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.PredictionTarget;

@Repository
public interface PredictionTargetRepository extends PagingAndSortingRepository<PredictionTarget, Long> {

}
