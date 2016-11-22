package com.talytica.common.service;

import org.springframework.stereotype.Service;
import org.json.JSONObject;

@Service
public interface AddressService {
	
	public void validate(JSONObject address);
	
	public JSONObject getAddressFromLatLng(double lat, double lng); 
	
}
