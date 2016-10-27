package com.employmeo.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.employmeo.data.model.SurveySection;
import com.employmeo.data.model.SurveySectionPK;

@Repository
public interface SurveySectionRepository extends PagingAndSortingRepository<SurveySection, SurveySectionPK> {

}
