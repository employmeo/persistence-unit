package com.employmeo.data.service;

import java.util.*;

import org.springframework.data.domain.Page;


import com.employmeo.data.model.*;

import lombok.NonNull;

public interface GraderService {

	Grader getGraderByUuid(@NonNull UUID uuId);

	Grader getGraderById(@NonNull Long graderId);

	Page<Grader> getGradersByUserId(@NonNull Long userId);

	Page<Grader> getGradersByUserId(@NonNull Long userId, int pageNumber, int pageSize);
	
	List<Question> getQuestionsByGraderUuid(@NonNull UUID uuId);

	List<Question> getQuestionsByGraderId(@NonNull Long graderId);
	
	Grader save(Grader grader);

	Grade saveGrade(Grade grade);
	
	List<Question> getCriteriaByQuestionId(@NonNull Long questionId);

}