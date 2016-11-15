package com.employmeo.data.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Respondant;

@Repository
public interface RespondantRepository extends PagingAndSortingRepository<Respondant, Long> {

	@Query
	public Respondant findByRespondantUuid(UUID respondantUuid);

	@Query
	public Respondant findByAccountSurveyIdAndPayrollId(Long accountSurveyId, String payrollId);

	@Query
	public Page<Respondant> findAllByAccountId(Long accountId, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndLocationId(Long accountId, Long locationId, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndPositionId(Long accountId, Long positionId, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndRespondantStatusIn(Long accountId, List<Integer> respondantStatuses, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndRespondantStatusBetweenAndCreatedDateBetween(Long accountId, Integer statusLow, Integer statusHigh, Timestamp fromDate, Timestamp toDate, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndPositionIdAndRespondantStatusBetweenAndCreatedDateBetween(Long accountId, Long positionId, Integer statusLow, Integer statusHigh, Timestamp fromDate, Timestamp toDate, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndLocationIdAndRespondantStatusBetweenAndCreatedDateBetween(Long accountId, Long locationId, Integer statusLow, Integer statusHigh, Timestamp fromDate, Timestamp toDate, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndLocationIdAndPositionIdAndRespondantStatusBetweenAndCreatedDateBetween(Long accountId, Long locationId, Long positionId, Integer statusLow, Integer statusHigh, Timestamp fromDate, Timestamp toDate, Pageable  pageRequest);

	@Query
	public List<Respondant> findAllByRespondantStatusInOrderByFinishTimeDesc(List<Integer> scoringEligibleRespondantStatuses);

}