package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.*;

import lombok.NonNull;

public interface PredictionConfigurationService {

	Set<PredictionModel> getAllPredictionModels();

	PredictionModel save(PredictionModel predictionModel);

	PredictionModel getPredictionModelById(Long predictionModelId);

	PredictionTarget getPredictionTargetById(@NonNull Long predictionTargetId);

	PredictionTarget save(@NonNull PredictionTarget predictionTarget);

	Set<PredictionTarget> getAllPredictionTargets();

	PositionPredictionConfiguration getPositionPredictionConfigurationById(@NonNull Long positionPredictionConfigurationId);

	PositionPredictionConfiguration save(@NonNull PositionPredictionConfiguration positionPredictionConfiguration);

	Set<PositionPredictionConfiguration> getAllPositionPredictionConfigurations();

}