package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.PositionPredictionConfiguration;

@Repository
public interface PositionPredictionConfigRepository extends PagingAndSortingRepository<PositionPredictionConfiguration, Long> {

}
