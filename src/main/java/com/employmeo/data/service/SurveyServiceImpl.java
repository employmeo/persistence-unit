package com.employmeo.data.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.*;
import com.employmeo.data.repository.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
		Optional<Survey> survey = surveyRepository.findById(surveyId);
		if (survey.isPresent()) {
			log.debug("Retrieved for id {} entity {}", surveyId, survey.get().getName());
			return survey.get();
		}
		return null;
		//return surveyRepository.findOne(surveyId);
	}

// -------------------------

	@Override
	public Set<SurveySection> getAllSurveySections() {
		Set<SurveySection> surveySections = Sets.newHashSet(surveySectionRepository.findAll());
		log.debug("Retrieved all {} surveySections", surveySections.size());

		return surveySections;
	}

	@Override
	public List<SurveySection> getAllSurveySectionsSorted() {
		List<SurveySection> surveySections = Lists.newArrayList(surveySectionRepository.findAll(sortBySurveyId()));
		log.debug("Retrieved all {} surveySections", surveySections.size());
		return surveySections;
	}
	
    private Sort sortBySurveyId() {

        //return new Sort(Sort.Direction.ASC, "surveyId");
        return Sort.by(Sort.Direction.ASC, "surveyId");    	
    }

	@Override
	public SurveySection save(@NonNull SurveySection surveySection) {
		SurveySection savedSurveySection = surveySectionRepository.save(surveySection);
		log.debug("Saved surveySection {}", surveySection.getName());

		return savedSurveySection;
	}

	@Override
	public SurveySection getSurveySectionById(@NonNull SurveySectionPK surveySectionPK) {
		SurveySection surveySection = surveySectionRepository.findById(surveySectionPK).get();
		//SurveySection surveySection = surveySectionRepository.findOne(surveySectionPK);
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
		Iterable<SurveyQuestion> savedSurveyQuestions = surveyQuestionRepository.saveAll(surveyQuestions);
		//Iterable<SurveyQuestion> savedSurveyQuestions = surveyQuestionRepository.save(surveyQuestions);
		log.debug("Saved multiple surveyQuestions");
		return savedSurveyQuestions;
	}
	
	@Override
	public SurveyQuestion getSurveyQuestionById(@NonNull Long surveyQuestionId) {
		SurveyQuestion surveyQuestion = surveyQuestionRepository.findById(surveyQuestionId).get();
		//SurveyQuestion surveyQuestion = surveyQuestionRepository.findOne(surveyQuestionId);
		log.debug("Retrieved for id {} entity {}", surveyQuestionId, surveyQuestion);

		return surveyQuestion;
	}

	@Override
	public void removeQuestion(Long sqId) {
		SurveyQuestion sq = surveyQuestionRepository.findById(sqId).get();
		//SurveyQuestion sq = surveyQuestionRepository.findOne(sqId);
		Survey survey = getSurveyById(sq.getSurveyId());
		survey.getSurveyQuestions().remove(sq);
		surveyQuestionRepository.deleteById(sqId);
		//surveyQuestionRepository.delete(sqId);
		log.info("Deleted survey question {}", sqId);		
	}

	@Override
	public void removeSection(SurveySectionPK id) {

    	Survey survey = getSurveyById(id.getSurveyId());
    	SurveySection surveySection = getSurveySectionById(id);
    	survey.getSurveySections().remove(surveySection);
		surveySectionRepository.deleteById(id);
		//surveySectionRepository.delete(id);
		log.info("Deleted survey section {}", id);	
	}

	@Override
	public void delete(Long surveyId) {
		surveyRepository.deleteById(surveyId);
		//surveyRepository.delete(surveyId);
		log.info("Deleted survey {}", surveyId);		
	}

	@Override
	public Set<Survey> getAllAvailableSurveys(Integer availability) {
		log.debug("Getting Surveys for Availability level: {}",availability);
		return surveyRepository.findByAvailabilityLessThanOrderByAvailabilityAsc(availability);
	}

}
