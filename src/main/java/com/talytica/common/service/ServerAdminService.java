package com.talytica.common.service;

import org.json.JSONObject;

public interface ServerAdminService {
	
	public boolean clearRemoteCache(String server);
	
	public JSONObject serverHealthCheck(String server);
	
}
