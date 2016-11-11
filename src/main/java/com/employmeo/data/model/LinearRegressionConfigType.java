package com.employmeo.data.model;

public enum LinearRegressionConfigType {
	INTERCEPT(1),
	COEFFICIENT(2),
	MEAN(3),
	STD_DEV(4),
	POPULATION(5);

	private Integer typeId;

	private LinearRegressionConfigType(Integer typeId) {
		this.typeId = typeId;
	}

	public static LinearRegressionConfigType getConfigType(Integer typeId) {

        if (typeId == null) {
            return null;
        }

        for (LinearRegressionConfigType configType : LinearRegressionConfigType.values()) {
            if (typeId.equals(configType.getTypeId())) {
                return configType;
            }
        }
        throw new IllegalArgumentException("No such LinearRegressionConfigType configured for id " + typeId);
    }

    public int getTypeId() {
        return typeId;
    }
}