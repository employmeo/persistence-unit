package com.employmeo.data.service;

import java.util.List;
import java.util.Set;

import com.employmeo.data.model.*;

import lombok.NonNull;

public interface SurveyService {

	Set<Survey> getAllSurveys();
	
	Set<Survey> getAllAvailableSurveys(Integer availability);

	Survey save(@NonNull Survey survey);

	Survey getSurveyById(@NonNull Long surveyId);

	Set<SurveySection> getAllSurveySections();
	
	List<SurveySection> getAllSurveySectionsSorted();

	SurveySection save(@NonNull SurveySection surveySection);

	SurveySection getSurveySectionById(@NonNull SurveySectionPK surveySectionPK);

	Set<SurveyQuestion> getAllSurveyQuestions();

	SurveyQuestion save(@NonNull SurveyQuestion surveyQuestion);
	
	Iterable<SurveyQuestion> save(@NonNull Iterable<SurveyQuestion> surveyQuestions);

	SurveyQuestion getSurveyQuestionById(@NonNull Long surveyQuestionId);
	
	void removeQuestion(Long sqId);
	
	void removeSection(SurveySectionPK id);
	
	void delete(Long surveyId);

}