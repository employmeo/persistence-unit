package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Prediction;

@Repository
public interface PredictionRepository extends PagingAndSortingRepository<Prediction, Long> {

}
