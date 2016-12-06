package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.QuestionType;

@Repository
public interface QuestionTypeRepository extends PagingAndSortingRepository<QuestionType, Long> {

}
