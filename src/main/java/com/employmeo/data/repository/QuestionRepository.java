package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Question;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {

}
