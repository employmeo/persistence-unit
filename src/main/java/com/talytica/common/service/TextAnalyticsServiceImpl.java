package com.talytica.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.JSONArray;
import org.json.JSONObject;

@Slf4j
@Service
public class TextAnalyticsServiceImpl implements TextAnalyticsService {
	
	private String WATSON_API = "https://gateway.watsonplatform.net/natural-language-understanding/api";
	private String ANALYZE = "/v1/analyze?version=2017-02-27";
	
	@Value("${com.talytica.apis.watson.sentiment.user:null}")
	private String WATSON_USER;
	
	@Value("${com.talytica.apis.watson.sentiment.pass:null}")
	private String WATSON_PASS;
	
	
	@Override
	public JSONObject analyzeText(JSONObject params){
		log.debug("Using config: {}", params);
		
		Client client = ClientBuilder.newClient();
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(WATSON_USER, WATSON_PASS);
		client.register(feature);
		JSONObject sentiment = new JSONObject();
		WebTarget target = client.target(WATSON_API+ANALYZE);
		try {
			javax.ws.rs.core.Response result = target.request(MediaType.APPLICATION_JSON)
			.post( Entity.entity(params.toString(), MediaType.APPLICATION_JSON));
			
			if (result.getStatus() >= 300) {
				log.error("Failed to call analyze API {}", result.getStatusInfo().getReasonPhrase());
				log.error(result.readEntity(String.class));
			} else {
				String resp = result.readEntity(String.class);
				log.debug("Post Media request {} resulted in: \n {}",target.toString(), resp);
				sentiment = new JSONObject(resp).getJSONObject("sentiment");
				
			}
		} catch (Exception e) {
			log.error("Failed to analyze {}, with {} ", params, e.getMessage());
		}	
		
		return sentiment;
	}

	@Override
	public Double analyzeSentiment(String text) {
		
		JSONObject params = new JSONObject();
		params.put("text",text);
		JSONObject features = new JSONObject();
		JSONObject sentiment = new JSONObject();
		features.put("sentiment",sentiment);
		params.put("features", features);
		JSONObject result = analyzeText(params);
		
		Double score = result.getJSONObject("document").optDouble("score");
		return normalizeSentimentScore(score);
	}
	
	@Override
	public Double normalizeSentimentScore(Double watsonScore) {
		return 1d - Math.sqrt(1d - watsonScore)/Math.sqrt(2d);
	}

	
	@Override
	public JSONObject analyzeSentimentForTarget(String text, String target) {
		JSONObject params = new JSONObject();
		params.put("text",text);
		JSONObject features = new JSONObject();
		JSONObject sentiment = new JSONObject();
		JSONArray targets = new JSONArray();
		targets.put(target);
		sentiment.put("targets", targets);
		features.put("sentiment",sentiment);
		params.put("features", features);
		return analyzeText(params);
	}

	@Override
	public JSONObject analyzeSentimentForTarget(String text, String[] targets) {
		JSONObject params = new JSONObject();
		params.put("text",text);
		JSONObject features = new JSONObject();
		JSONObject sentiment = new JSONObject();
		JSONArray targetlist = new JSONArray();
		for (int i=0;i<targets.length;i++) {
			targetlist.put(targets[i]);
		}
		sentiment.put("targets", targetlist);
		features.put("sentiment",sentiment);
		params.put("features", features);
		return analyzeText(params);
	}
		
	@PostConstruct
	private void logConfiguration() {
		if ("null".equals(WATSON_USER)) log.warn("--- TEXT ANALYTICS SERVICE UNAVAILABLE - NO USER CONFIGURED ---");
	}
	
}
