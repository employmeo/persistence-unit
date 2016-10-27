package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.RespondantScore;
import com.employmeo.data.model.RespondantScorePK;

@Repository
public interface RespondantScoreRepository extends PagingAndSortingRepository<RespondantScore, RespondantScorePK> {

}
