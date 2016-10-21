package com.employmeo.data.model.identifier;

import lombok.Data;
 
@Data
public class CorefactorId implements Identity {
	private static final long serialVersionUID = 6732419629776476574L;
	private Long value;
	
	private CorefactorId(Long value) {
		this.value = value;
	}
	
	public static CorefactorId of(Long value) {
		return new CorefactorId(value);
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

