package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.LinearRegressionConfig;

@Repository
public interface LinearRegressionConfigRepository extends PagingAndSortingRepository<LinearRegressionConfig, Long> {

}
