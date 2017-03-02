package com.employmeo.data.service;

import java.util.*;

import com.employmeo.data.model.*;

import lombok.NonNull;

public interface PredictionModelService {

	public List<LinearRegressionConfig> getLinearRegressionConfiguration(@NonNull Long modelId);

	public PredictionModel getModelByName(@NonNull String modelName);
	
	public PredictionModel getModelById(@NonNull Long modelId);
	
	public Set<PredictionModel> getAllPredictionModels();
	
	public void delete(@NonNull PredictionModel model);

	public PredictionModel save(@NonNull PredictionModel model);
	
	public PredictionTarget getTargetById(@NonNull Long targetId);
	
	public PredictionTarget save(@NonNull PredictionTarget target);
	
	public void delete(@NonNull PredictionTarget target);
	
	public Set<PredictionTarget> getAllPredictionTargets();

}