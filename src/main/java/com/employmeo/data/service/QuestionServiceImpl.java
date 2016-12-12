package com.employmeo.data.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Answer;
import com.employmeo.data.model.Question;
import com.employmeo.data.repository.AnswerRepository;
import com.employmeo.data.repository.QuestionRepository;

import jersey.repackaged.com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class QuestionServiceImpl implements QuestionService  {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;


	@Override
	public Set<Question> getAllQuestions() {
		Set<Question> questions = Sets.newHashSet(questionRepository.findAll());
		log.debug("Retrieved all {} questions", questions.size());

		return questions;
	}

	@Override
	public Question save(@NonNull Question question) {
		Question savedQuestion = questionRepository.save(question);
		log.debug("Saved question {}", question);
		return savedQuestion;
	}

	@Override
	public Question getQuestionById(@NonNull Long questionId) {
		Question question = questionRepository.findOne(questionId);
		log.debug("Retrieved question by id {}", questionId);
		return question;
	}

	@Override
	public Set<Answer> getAllAnswers() {
		Set<Answer> answers = Sets.newHashSet(answerRepository.findAll());
		log.debug("Retrieved all {} answers", answers.size());

		return answers;
	}

	@Override
	public Answer save(@NonNull Answer answer) {
		Answer savedAnswer = answerRepository.save(answer);
		log.debug("Saved answer {}", answer);

		return savedAnswer;
	}

	@Override
	public Answer getAnswerById(@NonNull Long answerId) {
		Answer answer = answerRepository.findOne(answerId);
		log.debug("Retrieved for id {} entity {}", answerId, answer);

		return answer;
	}
}
