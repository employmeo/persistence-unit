package com.employmeo.data.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
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
	public List<Respondant> findAllByAccountSurveyIdAndPayrollIdOrderByRespondantStatusDescCreatedDateDesc(Long accountSurveyId, String payrollId);
	
	@Query
	public Respondant findByAtsId(String atsId);
	
	@Query
	public Respondant findByAccountIdAndAtsId(Long accountId, String atsId);
	
	@Query
	public Respondant findByPersonIdAndPositionId(Long personId, Long positionId);

	@Query
	public Page<Respondant> findAllByAccountId(Long accountId, Pageable  pageRequest);
	
	@Query
	public Set<Respondant> findAllByBenchmarkId(Long benchmarkId);
	
	@Query
	public Set<Respondant> findAllByBenchmarkIdAndRespondantStatusGreaterThan(Long benchmarkId, Integer status);

	@Query
	public Page<Respondant> findAllByAccountIdAndLocationId(Long accountId, Long locationId, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndPositionId(Long accountId, Long positionId, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndRespondantStatusIn(Long accountId, List<Integer> respondantStatuses, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(Long accountId, Integer type, Integer statusLow, Integer statusHigh, Timestamp fromDate, Timestamp toDate, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndPositionIdAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(Long accountId, Long positionId, Integer type, Integer statusLow, Integer statusHigh, Timestamp fromDate, Timestamp toDate, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndLocationIdAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(Long accountId, Long locationId, Integer type, Integer statusLow, Integer statusHigh, Timestamp fromDate, Timestamp toDate, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndLocationIdInAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(Long accountId, List<Long> locationIds, Integer type, Integer statusLow, Integer statusHigh, Timestamp fromDate, Timestamp toDate, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndLocationIdAndPositionIdAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(Long accountId, Long locationId, Long positionId, Integer type, Integer statusLow, Integer statusHigh, Timestamp fromDate, Timestamp toDate, Pageable  pageRequest);

	@Query
	public Page<Respondant> findAllByAccountIdAndLocationIdInAndPositionIdAndTypeAndRespondantStatusBetweenAndCreatedDateBetween(Long accountId, List<Long> locationIds, Long positionId, Integer type, Integer statusLow, Integer statusHigh, Timestamp fromDate, Timestamp toDate, Pageable  pageRequest);
	
	@Query
	public Page<Respondant> findAllByErrorStatus(Boolean errorStatus, Pageable pageRequest);
	
	@Query
	public Page<Respondant> findAllByAccountIdAndErrorStatus(Long accountId, Boolean errorStatus, Pageable pageRequest);
	
	@Query
	public Page<Respondant> findAllByRespondantStatusInAndErrorStatus(List<Integer> statuses, Boolean errorStatus, Pageable  pageRequest);
	
	@Query
	public Page<Respondant> findAllByAccountIdAndRespondantStatusInAndErrorStatus(Long accountId, List<Integer> statuses, Boolean errorStatus, Pageable  pageRequest);
	
	@Query
	public List<Respondant> findAllByRespondantStatusInOrderByFinishTimeDesc(List<Integer> scoringEligibleRespondantStatuses);

	@Modifying
	@Query("update Respondant respondant set respondant.errorStatus = ?1 where respondant.id = ?2")
	public void setErrorStatusById(Boolean status, Long id);
	
	@Modifying
	@Query("update Respondant respondant set respondant.errorStatus = ?1 where respondant.id in ?2")
	public void setErrorStatusByIds(Boolean status, List<Long> ids);
}