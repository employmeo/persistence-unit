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
		log.debug("Retrieved all {} surveys", surveys.size());

		return surveys;
	}

	@Override
	public Survey save(@NonNull Survey survey) {
		Survey savedSurvey = surveyRepository.save(survey);
		log.debug("Saved survey {}", survey.getName());

		return savedSurvey;
	}

	@Override
	public Survey getSurveyById(@NonNull Long surveyId) {
		Survey survey = surveyRepository.findOne(surveyId);
		log.debug("Retrieved for id {} entity {}", surveyId, survey.getName());

		return survey;
	}

// -------------------------

	@Override
	public Set<SurveySection> getAllSurveySections() {
		Set<SurveySection> surveySections = Sets.newHashSet(surveySectionRepository.findAll());
		log.debug("Retrieved all {} surveySections", surveySections.size());

		return surveySections;
	}

	@Override
	public SurveySection save(@NonNull SurveySection surveySection) {
		SurveySection savedSurveySection = surveySectionRepository.save(surveySection);
		log.debug("Saved surveySection {}", surveySection.getName());

		return savedSurveySection;
	}

	@Override
	public SurveySection getSurveySectionById(@NonNull SurveySectionPK surveySectionPK) {
		SurveySection surveySection = surveySectionRepository.findOne(surveySectionPK);
		log.debug("Retrieved for id {} entity {}", surveySectionPK, surveySection.getName());

		return surveySection;
	}

	// ------------------------------

	@Override
	public Set<SurveyQuestion> getAllSurveyQuestions() {
		Set<SurveyQuestion> surveyQuestions = Sets.newHashSet(surveyQuestionRepository.findAll());
		log.debug("Retrieved all {} surveyQuestions", surveyQuestions.size());

		return surveyQuestions;
	}

	@Override
	public SurveyQuestion save(@NonNull SurveyQuestion surveyQuestion) {
		SurveyQuestion savedSurveyQuestion = surveyQuestionRepository.save(surveyQuestion);
		log.debug("Saved surveyQuestion {}", surveyQuestion);

		return savedSurveyQuestion;
	}

	@Override
	public Iterable<SurveyQuestion> save(Iterable<SurveyQuestion> surveyQuestions) {
		Iterable<SurveyQuestion> savedSurveyQuestions = surveyQuestionRepository.save(surveyQuestions);
		log.debug("Saved multiple surveyQuestions");
		return savedSurveyQuestions;
	}
	
	@Override
	public SurveyQuestion getSurveyQuestionById(@NonNull Long surveyQuestionId) {
		SurveyQuestion surveyQuestion = surveyQuestionRepository.findOne(surveyQuestionId);
		log.debug("Retrieved for id {} entity {}", surveyQuestionId, surveyQuestion);

		return surveyQuestion;
	}

	@Override
	public void removeQuestion(Long sqId) {
		SurveyQuestion sq = surveyQuestionRepository.findOne(sqId);
		Survey survey = getSurveyById(sq.getSurveyId());
		survey.getSurveyQuestions().remove(sq);
		surveyQuestionRepository.delete(sqId);
		log.info("Deleted survey question {}", sqId);		
	}

	@Override
	public void removeSection(SurveySectionPK id) {

    	Survey survey = getSurveyById(id.getSurveyId());
    	SurveySection surveySection = getSurveySectionById(id);
    	survey.getSurveySections().remove(surveySection);
		surveySectionRepository.delete(id);
		log.info("Deleted survey section {}", id);	
	}

	@Override
	public void delete(Long surveyId) {
		surveyRepository.delete(surveyId);
		log.info("Deleted survey {}", surveyId);		
	}

}
