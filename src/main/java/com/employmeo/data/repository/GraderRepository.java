package com.employmeo.data.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

}
