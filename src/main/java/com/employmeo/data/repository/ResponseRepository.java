package com.employmeo.data.repository;

import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Response;

@Repository
public interface ResponseRepository extends PagingAndSortingRepository<Response, Long> {

  Set<Response> findAllByRespondantId(Long respondantId);
  
}
