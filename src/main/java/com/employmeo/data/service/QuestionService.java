package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.Answer;
import com.employmeo.data.model.Question;

import lombok.NonNull;

public interface QuestionService {

	Set<Question> getAllQuestions();

	Question save(@NonNull Question question);

	Question getQuestionById(@NonNull Long questionId);

	Set<Answer> getAllAnswers();

	Answer save(@NonNull Answer answer);

	Answer getAnswerById(@NonNull Long answerId);
	
	void deleteQuestion(Long questionId);
	
	void deleteAnswer(Long answerId);
	
	void clearCache();

}