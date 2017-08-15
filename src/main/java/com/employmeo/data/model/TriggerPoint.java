package com.employmeo.data.model;

import lombok.*;

public enum TriggerPoint {
	CREATED(Respondant.STATUS_CREATED),
	PRESCREEN(Respondant.STATUS_PRESCREEN),
	GRADERSSENT(Respondant.STATUS_UNGRADED),
	POSTPREDICTED(Respondant.STATUS_PREDICTED);

	@Getter
	private Integer value;

	private TriggerPoint(Integer value) {
		this.value = value;
	}

	public static TriggerPoint getByValue(@NonNull Integer value) {
        for (TriggerPoint triggerPoint : TriggerPoint.values()) {
            if (value == triggerPoint.getValue()) {
                return triggerPoint;
            }
        }
        throw new IllegalArgumentException("No such TriggerPoint configured: " + value);
	}
}
