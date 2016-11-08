package com.employmeo.data.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.*;
import com.employmeo.data.repository.*;

import jersey.repackaged.com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SurveyServiceImpl implements SurveyService  {

	@Autowired
	private SurveyRepository surveyRepository;
	@Autowired
	private SurveySectionRepository surveySectionRepository;
	@Autowired
	private SurveyQuestionRepository surveyQuestionRepository;


	@Override
	public Set<Survey> getAllSurveys() {
		Set<Survey> surveys = Sets.newHashSet(surveyRepository.findAll());
		log.debug("Retrieved all {} surveys", surveys);

		return surveys;
	}

	@Override
	public Survey save(@NonNull Survey survey) {
		Survey savedSurvey = surveyRepository.save(survey);
		log.debug("Saved survey {}", survey);

		return savedSurvey;
	}

	@Override
	public Survey getSurveyById(@NonNull Long surveyId) {
		Survey survey = surveyRepository.findOne(surveyId);
		log.debug("Retrieved for id {} entity {}", surveyId, survey);

		return survey;
	}

// -------------------------

	@Override
	public Set<SurveySection> getAllSurveySections() {
		Set<SurveySection> surveySections = Sets.newHashSet(surveySectionRepository.findAll());
		log.debug("Retrieved all {} surveySections", surveySections);

		return surveySections;
	}

	@Override
	public SurveySection save(@NonNull SurveySection surveySection) {
		SurveySection savedSurveySection = surveySectionRepository.save(surveySection);
		log.debug("Saved surveySection {}", surveySection);

		return savedSurveySection;
	}

	@Override
	public SurveySection getSurveySectionById(@NonNull SurveySectionPK surveySectionPK) {
		SurveySection surveySection = surveySectionRepository.findOne(surveySectionPK);
		log.debug("Retrieved for id {} entity {}", surveySectionPK, surveySection);

		return surveySection;
	}

	// ------------------------------

	@Override
	public Set<SurveyQuestion> getAllSurveyQuestions() {
		Set<SurveyQuestion> surveyQuestions = Sets.newHashSet(surveyQuestionRepository.findAll());
		log.debug("Retrieved all {} surveyQuestions", surveyQuestions);

		return surveyQuestions;
	}

	@Override
	public SurveyQuestion save(@NonNull SurveyQuestion surveyQuestion) {
		SurveyQuestion savedSurveyQuestion = surveyQuestionRepository.save(surveyQuestion);
		log.debug("Saved surveyQuestion {}", surveyQuestion);

		return savedSurveyQuestion;
	}

	@Override
	public SurveyQuestion getSurveyQuestionById(@NonNull Long surveyQuestionId) {
		SurveyQuestion surveyQuestion = surveyQuestionRepository.findOne(surveyQuestionId);
		log.debug("Retrieved for id {} entity {}", surveyQuestionId, surveyQuestion);

		return surveyQuestion;
	}
}