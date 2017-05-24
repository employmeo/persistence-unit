package com.talytica.common.service;

import org.json.JSONObject;

public interface TextAnalyticsService {
	
	public JSONObject analyzeText(JSONObject config);
	public Double analyzeSentiment(String text);
	public JSONObject analyzeSentimentForTarget(String text, String target);
	public JSONObject analyzeSentimentForTarget(String text, String[] targets);
	public Double normalizeSentimentScore(Double watsonScore);
	
}
