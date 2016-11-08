package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.AccountSurvey;

@Repository
public interface AccountSurveyRepository extends PagingAndSortingRepository<AccountSurvey, Long> {

}
