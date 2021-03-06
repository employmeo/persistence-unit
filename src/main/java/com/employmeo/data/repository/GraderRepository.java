package com.employmeo.data.repository;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Grader;

import lombok.NonNull;

@Repository
public interface GraderRepository extends PagingAndSortingRepository<Grader, Long> {

	@Query
	Grader findByUuId(@NonNull UUID uuId);

	@Query
	public Page<Grader> findAllByUserId(Long userId, Pageable pageRequest);

	@Query
	public List<Grader> findAllByRespondantId(Long respondantId);
	
	@Modifying
	@Query("update Grader grader set grader.summaryScore = ?1 where grader.id = ?2")
	public void modifySummaryById(String summary, Long id);

	@Modifying
	@Query("update Grader grader set grader.relationship = ?1 where grader.id = ?2")
	public void modifyRelationshipById(String status, Long id);
	
	//TODO: Fix Me !!
	@Query("SELECT g from Grader g WHERE g.userId = ?1 AND g.status IN ?2 AND g.createdDate BETWEEN ?3 and ?4")
	public Page<Grader> findAllByUserIdAndStatusInAndCreatedDateBetween(@NonNull Long userId,
																  List<Integer> status,
																  Date from,
																  Date to,
			                                                      Pageable pageRequest);

}
