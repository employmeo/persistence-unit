package com.employmeo.data.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Grade;
import com.employmeo.data.model.Grader;
import com.employmeo.data.model.Question;
import com.employmeo.data.repository.CriterionRepository;
import com.employmeo.data.repository.GradeRepository;
import com.employmeo.data.repository.GraderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class GraderServiceImpl implements GraderService {
	
	private static final int DEFAULT_PAGE_SIZE = 100;
	private static final int PAGE_ONE = 1;
	
	@Autowired
	GraderRepository graderRepository;
	
	@Autowired
	GradeRepository gradeRepository;
	
	@Autowired
	CriterionRepository criterionRepository;
	
	@Override
	public Grader getGraderByUuid(UUID uuId) {
		return graderRepository.findByUuId(uuId);
	}

	@Override
	public Grader getGraderById(Long graderId) {
		return graderRepository.findOne(graderId);
	}

	@Override
	public Page<Grader> getGradersByUserId(Long userId) {
		
		return getGradersByUserId(userId, PAGE_ONE, DEFAULT_PAGE_SIZE);
	}
	
	@Override
	public Page<Grader> getGradersByUserId(Long userId, int pageNumber, int pageSize) {
		Pageable  pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "id");
		
		return graderRepository.findAllByUserId(userId, pageRequest);
	}


	@Override
	public List<Question> getQuestionsByGraderUuid(UUID uuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> getQuestionsByGraderId(Long graderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Grader save(Grader grader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Grade saveGrade(Grade grade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> getCriteriaByQuestionId(Long questionId) {
		// TODO Auto-generated method stub
		return null;
	}

}
