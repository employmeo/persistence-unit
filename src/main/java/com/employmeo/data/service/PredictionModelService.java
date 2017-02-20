package com.employmeo.data.service;

import java.util.*;

import com.employmeo.data.model.*;

import lombok.NonNull;

public interface PredictionModelService {

	public List<LinearRegressionConfig> getLinearRegressionConfiguration(@NonNull Long modelId);

	public PredictionModel getModelByName(@NonNull String modelName);
	
	public PredictionModel getModelById(@NonNull Long modelId);

}