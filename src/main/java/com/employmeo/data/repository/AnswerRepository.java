package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Answer;

@Repository
public interface AnswerRepository extends PagingAndSortingRepository<Answer, Long> {

}
