package com.employmeo.data.service;

import java.util.*;

import org.springframework.data.domain.Page;


import com.employmeo.data.model.*;

import lombok.NonNull;

public interface GraderService {

	Grader getGraderByUuid(@NonNull UUID uuId);

	Grader getGraderById(@NonNull Long graderId);

	ReferenceCheckConfig getReferenceCheckConfigById(Long rcConfigId);

	List<Grade> getGradesByGraderId(@NonNull Long graderId);
	
	List<Grade> getAllGradesByRespondantId(Long respondantId);
	
	Page<Grader> getGradersByUserId(@NonNull Long userId);

	Page<Grader> getGradersByUserId(@NonNull Long userId, int pageNumber, int pageSize);
	
	Page<Grader> getGradersByUserIdStatusAndDates(@NonNull Long userId, List<Integer> status, Date from, Date to);

	Page<Grader> getGradersByUserIdStatusAndDates(@NonNull Long userId, List<Integer> status, Date from, Date to, int pageNumber, int pageSize);
	
	List<Grader> getGradersByRespondantId(@NonNull Long respondantId);

	List<Question> getQuestionsByGraderUuid(@NonNull UUID uuId);

	List<Question> getQuestionsByGraderId(@NonNull Long graderId);
	
	Grader save(Grader grader);

	void setSummary(Long graderId, String summary);

	void setRelationship(Long graderId, String relationship);
	
	Grade saveGrade(Grade grade);
	
	List<Question> getCriteriaByQuestionId(@NonNull Long questionId);
	
	List<Criterion> getCriteriaListByQuestionId(Long questionId);
	
	List<Question> getSummaryCriteriaByGraderId(@NonNull Long graderId);

}