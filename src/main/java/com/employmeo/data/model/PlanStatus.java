package com.employmeo.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PlanStatus {
	EFFECTIVE(1, "Effective"),
	SCHEDULED(2, "Scheduled"),
	SUSPENDED(3, "On Hold"),
	EXPIRED(4, "Expired");

	@Getter
	private Integer statusId;
	@Getter
	private String label;

	public static PlanStatus getStatus(Integer statusId) {
        if (statusId == null) {
            return null;
        }

        for (PlanStatus status : PlanStatus.values()) {
            if (statusId.equals(status.getStatusId())) {
                return status;
            }
        }
        throw new IllegalArgumentException("No such PlanStatus configured for id " + statusId);
    }
    
}