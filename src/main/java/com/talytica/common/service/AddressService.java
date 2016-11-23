package com.talytica.common.service;

import org.json.JSONObject;

public interface AddressService {
	
	public void validate(JSONObject address);
	
	public JSONObject getAddressFromLatLng(double lat, double lng); 
	
}
