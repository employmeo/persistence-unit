package com.employmeo.data.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.AccountSurvey;

@Repository
public interface AccountSurveyRepository extends PagingAndSortingRepository<AccountSurvey, Long> {

	@Query
	public AccountSurvey findByUuId(UUID UuId);
	
	@Query
	public List<AccountSurvey> findAllBySurveyIdAndAccountIdAndAccountSurveyStatusAndTypeIn(Long surveyId, Long accountId, int status,
			List<Integer> types);
	
}
