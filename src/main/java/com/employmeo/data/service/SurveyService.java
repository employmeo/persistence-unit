package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.*;

import lombok.NonNull;

public interface SurveyService {

	Set<Survey> getAllSurveys();

	Survey save(@NonNull Survey survey);

	Survey getSurveyById(@NonNull Long surveyId);

	Set<SurveySection> getAllSurveySections();

	SurveySection save(@NonNull SurveySection surveySection);

	SurveySection getSurveySectionById(@NonNull SurveySectionPK surveySectionPK);

	Set<SurveyQuestion> getAllSurveyQuestions();

	SurveyQuestion save(@NonNull SurveyQuestion surveyQuestion);

	SurveyQuestion getSurveyQuestionById(@NonNull Long surveyQuestionId);

}