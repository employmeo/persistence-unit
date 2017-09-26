package com.employmeo.data.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Response;

@Repository
public interface ResponseRepository extends PagingAndSortingRepository<Response, Long> {

  @Query
  Set<Response> findAllByRespondantId(Long respondantId);
  
  @Query
  List<Response> findAllByRespondantIdAndQuestionIdOrderByCreatedDesc(Long respondantId, Long questionId);

  @Query
  Set<Response> findByRespondantIdAndQuestionIdIn(Long respondantId, List<Long> questionIds);
}
