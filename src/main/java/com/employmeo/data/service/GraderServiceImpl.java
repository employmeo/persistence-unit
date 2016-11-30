package com.employmeo.data.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.*;
import com.employmeo.data.repository.*;

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

	@Autowired
	QuestionService questionService;

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
		Grader grader = graderRepository.findByUuId(uuId);
		return getCriteriaByQuestionId(grader.getQuestionId());
	}

	@Override
	public List<Question> getCriteriaByQuestionId(Long questionId) {
		List<Criterion> criteria =  criterionRepository.findAllBySurveyQuestionIdOrderBySequenceAsc(questionId);
		List<Question> questions = new ArrayList<>();
		for (Criterion criterion : criteria) {
			Question question = questionService.getQuestionById(criterion.getGraderQuestionId());
			questions.add(question);
		}
		return questions;
	}

	@Override
	public List<Question> getQuestionsByGraderId(Long graderId) {
		Grader grader = graderRepository.findOne(graderId);
		return getCriteriaByQuestionId(grader.getQuestionId());
	}

	@Override
	public Grader save(Grader grader) {
		Grader savedGrader = graderRepository.save(grader);
		log.debug("Saved Grader {}", savedGrader);
		return savedGrader;
	}

	@Override
	public Grade saveGrade(Grade grade) {
		Grade savedGrade = gradeRepository.save(grade);
		log.debug("Saved Grader {}", savedGrade);
		return savedGrade;
	}

	@Override
	public List<Grade> getGradesByGraderId(Long graderId) {
		return gradeRepository.findAllByGraderId(graderId);
	}

	@Override
	public List<Grader> getGradersByRespondantId(Long respondantId) {
		return graderRepository.findAllByRespondantId(respondantId);
	}

	@Override
	public Page<Grader> getGradersByUserIdStatusAndDates(Long userId, List<Integer> status, Date from, Date to) {
		return getGradersByUserIdStatusAndDates(userId, status, from, to, PAGE_ONE, DEFAULT_PAGE_SIZE);
	}

	@Override
	public Page<Grader> getGradersByUserIdStatusAndDates(Long userId, List<Integer>  status, Date from, Date to, int pageNumber, int pageSize) {
		Pageable  pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "id");
		log.debug("Finding graders for userId {} and statuses {} between dates {} and {}", userId, status, from, to);
		return graderRepository.findAllByUserIdAndStatusInAndCreatedDateBetween(userId, status, from, to, pageRequest);
	}


}
