package com.talytica.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

@Slf4j
@Service
public class SpeechToTextServiceImpl implements SpeechToTextService {
	
	@Value("https://speech.googleapis.com/v1beta1/speech:syncrecognize")
	private String SPEECH_TO_TEXT_SERVICE;

	@Value("${com.talytica.apis.googlemaps}")
	private String googleApiKey = System.getenv("GOOGLE_MAPS_KEY");

	private String getGoogleApiKey() {
		return googleApiKey;
	}

	@Override
	public String translateMedia(String mediaUrl) {

		JSONObject syncRequest = new JSONObject();
		syncRequest.put("config", configForTarget(mediaUrl));
		
		JSONObject audio = new JSONObject();
		audio.put("uri", mediaUrl);
		syncRequest.put("audio", audio);
		
		JSONObject alternative = null;
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(SPEECH_TO_TEXT_SERVICE).queryParam("key", getGoogleApiKey());
		try {
			;
			Response result = target.request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(syncRequest.toString(), MediaType.APPLICATION_JSON));

			JSONObject json = new JSONObject(result.readEntity(String.class));
			JSONArray results = json.getJSONArray("alternatives");
			alternative = results.getJSONObject(0);
		} catch (Exception e) {
			log.error("{} (lookup failed) for {} ", e.getMessage(), syncRequest);
		}

		if (null != alternative) return alternative.getString("transcript");
		
		return null;
	}
	
	private JSONObject configForTarget(String mediaUrl) {

		JSONObject config = new JSONObject();
		config.put("encoding", "FLAC");
		config.put("sampleRate", 16000);
		config.put("languageCode", "en-US");
		return config;
	}

	

}
