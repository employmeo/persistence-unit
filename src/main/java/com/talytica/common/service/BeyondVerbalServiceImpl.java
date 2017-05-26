package com.talytica.common.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.employmeo.data.model.*;
import com.employmeo.data.repository.ApiTransactionRepository;
import com.employmeo.data.repository.ResponseRepository;
import com.employmeo.data.service.CorefactorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeyondVerbalServiceImpl implements BeyondVerbalService {
	
	@Value("${com.talytica.apis.beyondverbal.key:null}")
	private String BEYONDVERBAL_KEY;
	private String beyondVerbalToken = null;
	private Long tokenExpiration;
	private final String BV_API = "https://apiv3.beyondverbal.com/v3/recording";
	private final String START = "/start";
	private final String ANALYSIS = "/analysis";
	private Long TEMPER_COREFACTOR = 1012l;
	private NormalDistribution temperDist;
	private Double temperMean = 29d;
	private Double temperDev = 12d;
	private Long VALENCE_COREFACTOR = 1013l;
	private NormalDistribution valenceDist;
	private Double valenceMean = 27.5d;
	private Double valenceDev = 13.77d; 
	private Long AROUSAL_COREFACTOR = 1014l;
	
	@Autowired
	private CorefactorService corefactorService;

	@Autowired
	private ApiTransactionRepository apiTransactionRepository;
	
	@Autowired
	private ResponseRepository responseRepository;
	
	@Override
	public JSONObject analyzeResponse(Long responseId) {

		Response response = responseRepository.findOne(responseId);
		log.debug("New Analysis requested for: {}" , response);
		String recordingId = startAnalysis(response.getId());	
		JSONObject json = analyzeMedia(response.getResponseMedia(), recordingId);

		if (recordingId != null) {
			ApiTransaction txn = new ApiTransaction();
			txn.setApiName(this.getClass().toString());
			txn.setObjectId(responseId);
			txn.setReferenceId(recordingId);
			apiTransactionRepository.save(txn);
		}
		
		return json;
		
	}	

	@Override
	public String startAnalysis(Long responseId) {
		String recordingId = null;	
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BV_API+START);
		try {
			JSONObject request = new JSONObject();
			JSONObject format = new JSONObject();
			JSONObject metadata = new JSONObject();
			format.put("type", "WAV");
			metadata.put("clientID", responseId);
			request.put("dataFormat", format);
			request.put("metadata", metadata);
			
			javax.ws.rs.core.Response result = target.request(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + getBeyondVerbalToken())
					.post(Entity.entity(request.toString(2), MediaType.APPLICATION_JSON));

			if (result.getStatus() >= 300) {
				log.error("Failed to call start API {}", result.getStatusInfo().getReasonPhrase());
			} else {
				String resp = result.readEntity(String.class);
				log.debug("Start Analysis request {} resulted in: \n {}",target.toString(), resp);
				JSONObject json = new JSONObject(resp);
				
				recordingId = json.optString("recordingId");
				
			}
		} catch (Exception e) {
			log.error("Failed to start analysis for response {}, with {} ", responseId, e.getMessage());
		}
			
		return recordingId;
	}
	
	@Override
	public JSONObject analyzeMedia(String responseMedia, String recordingId) {
		JSONObject json = new JSONObject();
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BV_API+"/"+recordingId);
		try {
			URL mediaUrl = new URL(responseMedia);
			javax.ws.rs.core.Response result = target.request(MediaType.APPLICATION_JSON)
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + getBeyondVerbalToken())
			.post( Entity.entity(mediaUrl.openStream(),MediaType.APPLICATION_OCTET_STREAM_TYPE));
			
			if (result.getStatus() >= 300) {
				log.error("Failed to call analyze API {}", result.getStatusInfo().getReasonPhrase());
				log.error(result.readEntity(String.class));
			} else {
				String resp = result.readEntity(String.class);
				log.debug("Post Media request {} resulted in: \n {}",target.toString(), resp);
				json = new JSONObject(resp);
				return json;
			}
		} catch (Exception e) {
			log.error("Failed to analyze {}, with {} ", recordingId, e.getMessage());
		}
		return json;		
	}
	
	
	@Override
	public JSONObject getAnalysis(String recordingId) {
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BV_API+"/"+recordingId+ANALYSIS);
		try {
			javax.ws.rs.core.Response result = target.request(MediaType.APPLICATION_JSON)
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + getBeyondVerbalToken()).get();
			
			if (result.getStatus() >= 300) {
				log.error("Failed to call analyze API {}", result.getStatusInfo().getReasonPhrase());
				log.error(result.readEntity(String.class));
			} else {
				String resp = result.readEntity(String.class);
				log.debug("Get Analysis request {} resulted in: \n {}",target.toString(), resp);
				JSONObject json = new JSONObject(resp);
				return json;
			}
		} catch (Exception e) {
			log.error("Failed to retrive analysis for {}, with {} ", recordingId, e.getMessage());
		}
		return null;		
	}
	
	
	public List<RespondantScore> analyzeResponses(List<Response> responses) {
		List<RespondantScore> scores = new ArrayList<RespondantScore>();
		Set<JSONObject> segments = new HashSet<JSONObject>();
		Long respondantId = null;
		
		for (Response response : responses) {
			respondantId = response.getRespondantId();
			JSONObject json = null;
			JSONArray analysisSegments = null;
			log.info("looking for {}, {}",this.getClass().toString(), response.getId());
			ApiTransaction txn = apiTransactionRepository.findFirstByApiNameAndObjectIdOrderByCreatedDateDesc(this.getClass().toString(), response.getId());
			log.info("found {}",txn);
			if (txn != null) json = getAnalysis(txn.getReferenceId());
			if (json == null) {
				log.debug("no recording id for {}", response);
				json = analyzeResponse(response.getId());
			}	
			JSONObject result = json.optJSONObject("result");
			if (null != result) analysisSegments = result.optJSONArray("analysisSegments");
			if (analysisSegments != null ) for (int i=0;i<analysisSegments.length();i++) {segments.add(analysisSegments.getJSONObject(i));}
		}
		log.debug("{} Responses analayzed as : {}", responses.size(), segments);

		Double totalduration = 0d;
		Double tempertotal = 0d;
		Double valencetotal = 0d;
		Double arousaltotal = 0d;
		HashMap<Corefactor,Double> hashtable =new HashMap<Corefactor,Double>();
		
		for (JSONObject segment : segments) {
			//long offset = segment.getLong("offset");
			Double duration = segment.getDouble("duration");
			totalduration += duration;
			JSONObject analysis = segment.getJSONObject("analysis");
			JSONObject temper = analysis.getJSONObject("Temper");
			tempertotal += duration * temper.getDouble("Value");
			JSONObject valence = analysis.getJSONObject("Valence");
			valencetotal += duration * valence.getDouble("Value");
			JSONObject arousal = analysis.getJSONObject("Arousal");
			arousaltotal += duration * arousal.getDouble("Value");
			
			JSONObject primary = analysis.getJSONObject("Mood").getJSONObject("Group11").getJSONObject("Primary");
			Corefactor primaryCorefactor = corefactorService.getByForeignId("BV"+primary.getLong("Id"));
			Double factorDuration = 0d;			
			if (hashtable.containsKey(primaryCorefactor)) factorDuration = hashtable.get(primaryCorefactor);
			factorDuration += 2d * duration;
			hashtable.put(primaryCorefactor, factorDuration);
			
			JSONObject secondary = analysis.getJSONObject("Mood").getJSONObject("Group11").getJSONObject("Secondary");
			Corefactor secondaryCorefactor = corefactorService.getByForeignId("BV"+secondary.getLong("Id"));
			factorDuration = 0d;			
			if (hashtable.containsKey(secondaryCorefactor)) factorDuration = hashtable.get(secondaryCorefactor);
			factorDuration += 1.25d * duration;// half value if duration isn't where we want it.
			hashtable.put(secondaryCorefactor, factorDuration);
		}
		
		if (totalduration > 0) {
			log.debug("Weighted Average Temper {} normalized to {}",tempertotal/totalduration,
					1d - 2d * Math.abs(0.5d- temperDist.cumulativeProbability(tempertotal/totalduration)));
			scores.add(scaleScore(
					respondantId,
					TEMPER_COREFACTOR,
					1d - 2d * Math.abs(0.5d- temperDist.cumulativeProbability(tempertotal/totalduration)),
					responses.size()));
			log.debug("Weighted Average Valence {} normalized to {}",valencetotal/totalduration,
					valenceDist.cumulativeProbability(valencetotal/totalduration));
			scores.add(scaleScore(respondantId,
					VALENCE_COREFACTOR,
					valenceDist.cumulativeProbability(valencetotal/totalduration),
					responses.size()));
			scores.add(scaleScore(
					respondantId,
					AROUSAL_COREFACTOR,
					arousaltotal/(100d*totalduration),
					responses.size()));
		}
			
		for (Map.Entry<Corefactor, Double> pair : hashtable.entrySet()) {
			RespondantScore moodScore = new RespondantScore();
			Corefactor moodFactor = pair.getKey();	
			Double value = 10 * pair.getValue() / totalduration; // sort of scales to 10?
			moodScore.setId(new RespondantScorePK(moodFactor.getId(),responses.get(0).getRespondantId()));
			moodScore.setValue(Math.max(Math.min(10d, value),1d)); /// force range to 1 to 10;
			moodScore.setQuestionCount(responses.size());
			scores.add(moodScore);
		}

		return scores;
	}
	
	private RespondantScore scaleScore(Long respondantId, Long corefactorId, Double normalScore, int count) {
		RespondantScore rs = new RespondantScore();
		Corefactor cf =corefactorService.findCorefactorById(corefactorId);
		rs.setId(new RespondantScorePK(corefactorId,respondantId));
		Double scaledValue = (cf.getHighValue()-cf.getLowValue()) * (normalScore) + cf.getLowValue();
		rs.setQuestionCount(count);
		rs.setValue(Math.max(cf.getLowValue(), Math.min(cf.getHighValue(), scaledValue)));
		log.debug("Score for {} converted from {} to {}",cf.getName(),normalScore,rs.getValue());
		return rs;
	}
	
	
	private String getBeyondVerbalToken() {
		
		if (this.tokenExpiration > System.currentTimeMillis()) return this.beyondVerbalToken;	
		log.debug("Token expired / not found");
		Form form = new Form();
		form.param("grant_type", "client_credentials");
		form.param("apikey", BEYONDVERBAL_KEY);
		Client client = ClientBuilder.newClient();
		javax.ws.rs.core.Response result = client.target("https://token.beyondverbal.com/token")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
		
		JSONObject json = new JSONObject(result.readEntity(String.class));

		this.tokenExpiration = System.currentTimeMillis() + (1000 * json.optLong("expires_in",0l));
		log.debug("token returned with expy: {}", new Date(this.tokenExpiration));		
		beyondVerbalToken = json.optString("access_token");
		return this.beyondVerbalToken;		
		
	}
	
	@PostConstruct
	private void logConfiguration() {
		this.tokenExpiration = System.currentTimeMillis(); // set token to expire now
		temperDist = new NormalDistribution(temperMean,temperDev);
		valenceDist = new NormalDistribution(valenceMean,valenceDev);
		if ("null".equals(BEYONDVERBAL_KEY)) log.warn("--- AUDIO ANALYTICS SERVICE UNAVAILABLE - NO USER CONFIGURED ---");
	}
	
}
