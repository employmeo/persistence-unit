package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.SurveyQuestion;

@Repository
public interface SurveyQuestionRepository extends PagingAndSortingRepository<SurveyQuestion, Long> {

}
