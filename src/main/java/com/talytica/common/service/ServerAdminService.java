package com.talytica.common.service;

import org.json.JSONObject;

public interface ServerAdminService {
	
	public void clearRemoteCache(String server);

	public void triggerPipeline(String action);
	
	public JSONObject serverHealthCheck(String server);
	
}
