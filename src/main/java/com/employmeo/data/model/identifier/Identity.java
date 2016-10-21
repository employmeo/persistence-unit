package com.employmeo.data.model.identifier;

import java.io.Serializable;

public interface Identity extends Serializable {

	public Long getLongValue();
	public String getStringValue();	
	public Object getObjectValue();
}
