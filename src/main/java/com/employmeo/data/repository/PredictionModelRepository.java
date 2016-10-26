package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.PredictionModel;

@Repository
public interface PredictionModelRepository extends PagingAndSortingRepository<PredictionModel, Long> {

}
