package com.employmeo.data.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Respondant;

@Repository
public interface RespondantRepository extends PagingAndSortingRepository<Respondant, Long> {

	@Query
	public Respondant findByRespondantUuid(UUID respondantUuid);

	@Query
	public List<Respondant> findAllByAccountIdOrderByIdDesc(Long accountId);

	@Query
	public List<Respondant> findAllByAccountIdAndLocationIdOrderByIdDesc(Long accountId, Long locationId);

	@Query
	public List<Respondant> findAllByAccountIdAndPositionIdOrderByIdDesc(Long accountId, Long positionId);

	@Query
	public List<Respondant> findAllByAccountIdAndRespondantStatusInOrderByIdDesc(Long accountId, List<Integer> respondantStatuses);
}
