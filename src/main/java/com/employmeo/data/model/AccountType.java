package com.employmeo.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AccountType {
	DEMO(1, "Demo"),
	TRIAL(2, "Trial"),
	SUBSCRIBER(3, "Subscriber");

	@Getter
	private Integer typeId;
	@Getter
	private String label;

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
    
}