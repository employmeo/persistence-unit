package com.talytica.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.employmeo.data.model.RespondantNVP;
import com.employmeo.data.model.Response;
import com.employmeo.data.service.RespondantService;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class SpeechToTextServiceImpl implements SpeechToTextService {
	
	private String WATSON_API = "https://stream.watsonplatform.net/speech-to-text/api";
	private String TRANSLATE = "/v1/recognize?continuous=true";
	private String NARROWBAND_US = "&model=en-US_NarrowbandModel";
	
	@Value("${com.talytica.apis.watson.speech.user:null}")
	private String WATSON_USER;
	
	@Value("${com.talytica.apis.watson.speech.pass:null}")
	private String WATSON_PASS;
	
	@Autowired
	private RespondantService respondantService;
	
	
	@Override
	public String translateMedia(String mediaUrl, String contentType) {
		String translationModel = NARROWBAND_US;
		StringBuffer sb = new StringBuffer();		
		Client client = ClientBuilder.newClient();
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(WATSON_USER, WATSON_PASS);
		client.register(feature);
		
		WebTarget target = client.target(WATSON_API+TRANSLATE+translationModel);
		try {
			URL url = new URL(mediaUrl);
			javax.ws.rs.core.Response result = target.request(MediaType.APPLICATION_JSON)
			.post( Entity.entity(url.openStream(), contentType));
			
			if (result.getStatus() >= 300) {
				log.error("Failed to call analyze API {}", result.getStatusInfo().getReasonPhrase());
				log.error(result.readEntity(String.class));
			} else {
				String resp = result.readEntity(String.class);
				log.debug("Post Media request {} resulted in: \n {}",target.toString(), resp);
				JSONArray results =  new JSONObject(resp).getJSONArray("results");
				for (int i=0;i<results.length();i++) {
					JSONArray alternatives = results.getJSONObject(i).getJSONArray("alternatives");
					sb.append(alternatives.getJSONObject(0).getString("transcript"));
				}
			}
		} catch (Exception e) {
			log.error("Failed to analyze {}, with {} ", mediaUrl, e.getMessage());
		}	
		
		return sb.toString();	
	}

	@Override
	public String translateWav(String responseMedia) {
		return translateMedia(responseMedia, AUDIO_WAV);		
	}
	
	@Override
	public String translateMp3(String responseMedia) {
		return translateMedia(responseMedia, AUDIO_MP3);
	}
	@Override
	public String translateWebm(String responseMedia) {
		return translateMedia(responseMedia, VIDEO_WEBM);
	}

	@Override
	public Set<RespondantNVP> convertToTextFeatures(List<Response> responses, String contentType) {

		Set<RespondantNVP> nvps = new HashSet<RespondantNVP>();
		for (Response response : responses) {
			String text = translateMedia(response.getResponseMedia(),contentType);
			if ((null != text) && (!text.isEmpty())) {
				RespondantNVP nvp = new RespondantNVP();
				nvp.setName(response.getQuestion().getQuestionText());
				nvp.setValue(text);
				nvp.setRespondantId(response.getRespondantId());
				nvps.add(respondantService.save(nvp));
			}
		}

		return nvps;
		
	}
	
	@PostConstruct
	private void logConfiguration() {
		if ("null".equals(WATSON_USER)) log.warn("--- SPEECH ANALYTICS SERVICE UNAVAILABLE - NO USER CONFIGURED ---");
	}
	
}
