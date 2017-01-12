package com.employmeo.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.Benchmark;

@Repository
public interface BenchmarkRepository extends PagingAndSortingRepository<Benchmark, Long> {
  
	@Query
	public List<Benchmark> findAllByAccountId(Long accountId);
	
	@Query
	public List<Benchmark> findAllByAccountIdAndStatusLessThan(Long accountId, Integer Status);

	@Query
	public List<Benchmark> findAllByPositionId(Long positionId);

}
