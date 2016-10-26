package com.employmeo.data.model;

import lombok.*;

public enum PredictionModelType {
	LINEAR_REGRESSION("linear_regression");

	@Getter
	private String value;

	private PredictionModelType(String value) {
		this.value = value;
	}

	public static PredictionModelType getByValue(@NonNull String value) {
        for (PredictionModelType modelType : PredictionModelType.values()) {
            if (value.equals(modelType.getValue())) {
                return modelType;
            }
        }
        throw new IllegalArgumentException("No such ModelType configured: " + value);
	}
}
