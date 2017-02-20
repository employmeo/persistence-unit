package com.employmeo.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.*;
import com.employmeo.data.repository.LinearRegressionConfigRepository;
import com.employmeo.data.repository.PredictionModelRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PredictionModelServiceImpl implements PredictionModelService{

	@Autowired
	private PredictionModelRepository predictionModelRepository;
	@Autowired
	private LinearRegressionConfigRepository linearRegressionConfigRepository;

	public List<LinearRegressionConfig> getLinearRegressionConfiguration(@NonNull Long modelId) {
		log.debug("Fetching linear regression configurations for modelName {}", modelId);

		PredictionModel predictionModel = getModelById(modelId);
		List<LinearRegressionConfig> configEntries = null;
		if(PredictionModelType.LINEAR_REGRESSION == predictionModel.getModelType()) {
			configEntries = linearRegressionConfigRepository.findByModelId(predictionModel.getModelId());
		} else {
			log.warn("Model {} is not a linear regression type model. Please review configurations.", predictionModel.getName());
			throw new IllegalStateException("Model " + predictionModel.getName() + " is not a linear regression type model. Please review setup.");
		}
		return configEntries;
	}

	@Override
	public PredictionModel getModelByName(@NonNull String modelName) {
		log.debug("Fetching prediction model by name {}", modelName);

		PredictionModel predictionModel = predictionModelRepository.findByName(modelName);
		log.debug("PredictionModel for modelName {} : {}", modelName, predictionModel);

		return predictionModel;
	}
	
	@Override
	public PredictionModel getModelById(@NonNull Long modelId) {
		log.debug("Fetching prediction model by name {}", modelId);

		PredictionModel predictionModel = predictionModelRepository.findOne(modelId);
		log.debug("PredictionModel for modelName {} : {}", modelId, predictionModel);

		return predictionModel;
	}

}
