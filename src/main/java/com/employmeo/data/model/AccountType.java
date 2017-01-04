package com.employmeo.data.model;

public enum AccountType {
	DEMO(1),
	TRIAL(2),
	SUBSCRIBER(3);

	private Integer typeId;

	private AccountType(Integer typeId) {
		this.typeId = typeId;
	}

	public static AccountType getAccountType(Integer typeId) {
        if (typeId == null) {
            return null;
        }

        for (AccountType configType : AccountType.values()) {
            if (typeId.equals(configType.getTypeId())) {
                return configType;
            }
        }
        throw new IllegalArgumentException("No such AccountType configured for id " + typeId);
    }

    public Integer getTypeId() {
        return typeId;
    }
}