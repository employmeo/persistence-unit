package com.talytica.common.service;

import java.util.List;

import org.json.JSONObject;

import com.employmeo.data.model.*;

public interface BeyondVerbalService {
	
	public JSONObject analyzeResponse(Long responseId);
	public String startAnalysis(Long responseId);
	public JSONObject analyzeMedia(String responseMedia, String recordingId) ;
	public JSONObject getAnalysis(String recordingId);
	public List<RespondantScore> analyzeResponses(List<Response> responses);

	
}
