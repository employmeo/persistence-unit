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
public class PredictionConfigurationServiceImpl implements PredictionConfigurationService  {

	@Autowired
	private PredictionModelRepository predictionModelRepository;
	@Autowired
	private PredictionTargetRepository predictionTargetRepository;
	@Autowired
	private PositionPredictionConfigRepository positionPredictionConfigRepository;


	@Override
	public Set<PredictionModel> getAllPredictionModels() {
		Set<PredictionModel> predictionModels = Sets.newHashSet(predictionModelRepository.findAll());
		log.debug("Retrieved all {} predictionModels", predictionModels);

		return predictionModels;
	}

	@Override
	public PredictionModel save(@NonNull PredictionModel predictionModel) {
		PredictionModel savedPredictionModel = predictionModelRepository.save(predictionModel);
		log.debug("Saved predictionModel {}", predictionModel);

		return savedPredictionModel;
	}

	@Override
	public PredictionModel getPredictionModelById(@NonNull Long predictionModelId) {
		PredictionModel predictionModel = predictionModelRepository.findOne(predictionModelId);
		log.debug("Retrieved for id {} entity {}", predictionModelId, predictionModel);

		return predictionModel;
	}

// ----------

	@Override
	public Set<PredictionTarget> getAllPredictionTargets() {
		Set<PredictionTarget> predictionTargets = Sets.newHashSet(predictionTargetRepository.findAll());
		log.debug("Retrieved all {} predictionTargets", predictionTargets);

		return predictionTargets;
	}

	@Override
	public PredictionTarget save(@NonNull PredictionTarget predictionTarget) {
		PredictionTarget savedPredictionTarget = predictionTargetRepository.save(predictionTarget);
		log.debug("Saved predictionTarget {}", predictionTarget);

		return savedPredictionTarget;
	}

	@Override
	public PredictionTarget getPredictionTargetById(@NonNull Long predictionTargetId) {
		PredictionTarget predictionTarget = predictionTargetRepository.findOne(predictionTargetId);
		log.debug("Retrieved for id {} entity {}", predictionTargetId, predictionTarget);

		return predictionTarget;
	}

	// -----------

	@Override
	public Set<PositionPredictionConfiguration> getAllPositionPredictionConfigurations() {
		Set<PositionPredictionConfiguration> positionPredictionConfigurations = Sets.newHashSet(positionPredictionConfigRepository.findAll());
		log.debug("Retrieved all {} positionPredictionConfigurations", positionPredictionConfigurations);

		return positionPredictionConfigurations;
	}

	@Override
	public PositionPredictionConfiguration save(@NonNull PositionPredictionConfiguration positionPredictionConfiguration) {
		PositionPredictionConfiguration savedPositionPredictionConfiguration = positionPredictionConfigRepository.save(positionPredictionConfiguration);
		log.debug("Saved positionPredictionConfiguration {}", positionPredictionConfiguration);

		return savedPositionPredictionConfiguration;
	}

	@Override
	public PositionPredictionConfiguration getPositionPredictionConfigurationById(@NonNull Long positionPredictionConfigurationId) {
		PositionPredictionConfiguration positionPredictionConfiguration = positionPredictionConfigRepository.findOne(positionPredictionConfigurationId);
		log.debug("Retrieved for id {} entity {}", positionPredictionConfigurationId, positionPredictionConfiguration);

		return positionPredictionConfiguration;
	}
}
