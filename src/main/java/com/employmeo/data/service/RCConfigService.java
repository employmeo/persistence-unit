package com.employmeo.data.service;

import com.employmeo.data.model.ReferenceCheckConfig;

public interface RCConfigService {
	
	ReferenceCheckConfig getRCConfig(Long rccId);
	Iterable<ReferenceCheckConfig> getAll();
	ReferenceCheckConfig save(ReferenceCheckConfig rcConfig);
	void deleteRCConfig(Long rccId);

}
