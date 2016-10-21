package com.employmeo.data.model.identifier;

import lombok.Data;

@Data
public class CorefactorDescriptionId implements Identity {
	private static final long serialVersionUID = -1417574865664019888L;
	private Long value;
	
	private CorefactorDescriptionId(Long value) {
		this.value = value;
	}
	
	public static CorefactorDescriptionId of(Long value) {
		return new CorefactorDescriptionId(value);
	}

	@Override
	public String getStringValue() {
		return String.valueOf(this.value);
	}

	@Override
	public Object getObjectValue() {
		return this.value;
	}

	@Override
	public Long getLongValue() {
		return this.value;
	}
}

