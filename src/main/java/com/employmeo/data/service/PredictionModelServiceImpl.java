package com.employmeo.data.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.*;
import com.employmeo.data.repository.LinearRegressionConfigRepository;
import com.employmeo.data.repository.PredictionModelRepository;
import com.employmeo.data.repository.PredictionTargetRepository;

import jersey.repackaged.com.google.common.collect.Lists;
import jersey.repackaged.com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PredictionModelServiceImpl implements PredictionModelService{

	@Autowired
	private PredictionModelRepository predictionModelRepository;
	
	@Autowired
	private PredictionTargetRepository predictionTargetRepository;
	
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
	public List<LinearRegressionConfig> getAllLinearRegressionConfigurations() {
		return Lists.newArrayList(linearRegressionConfigRepository.findAll());
	}
	
	@Override
	public LinearRegressionConfig getLinearRegressionConfigurationById(Long configId) {
		return linearRegressionConfigRepository.findOne(configId);
	}	
	
	@Override
	public LinearRegressionConfig save(LinearRegressionConfig config) {
		return linearRegressionConfigRepository.save(config);
	}

	@Override
	public void delete(LinearRegressionConfig config) {
		linearRegressionConfigRepository.delete(config);
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

	@Override
	public Set<PredictionModel> getAllPredictionModels() {
		return Sets.newHashSet(predictionModelRepository.findAll());
	}
	
	@Override
	public void delete(PredictionModel model) {
		predictionModelRepository.delete(model);		
	}

	@Override
	public PredictionModel save(PredictionModel model) {
		return predictionModelRepository.save(model);	
	}

	@Override
	public PredictionTarget getTargetById(Long targetId) {
		return predictionTargetRepository.findOne(targetId);
	}

	@Override
	public PredictionTarget save(PredictionTarget target) {
		return predictionTargetRepository.save(target);
	}

	@Override
	public void delete(PredictionTarget target) {
		predictionTargetRepository.delete(target);		
	}

	@Override
	public Set<PredictionTarget> getAllPredictionTargets() {
		return Sets.newHashSet(predictionTargetRepository.findAll());
	}
}
