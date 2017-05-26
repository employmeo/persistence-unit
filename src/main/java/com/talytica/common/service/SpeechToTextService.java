package com.talytica.common.service;

import java.util.List;
import java.util.Set;

import com.employmeo.data.model.RespondantNVP;
import com.employmeo.data.model.Response;

public interface SpeechToTextService {
	
	public static final String AUDIO_WAV = "audio/wav";
	public static final String AUDIO_MP3 = "audio/mp3";
	public static final String VIDEO_WEBM = "audio/webm";
	
	public String translateMedia(String mediaUrl, String contentType);
	public String translateWav(String mediaUrl);
	public String translateMp3(String mediaUrl);
	public String translateWebm(String mediaUrl);
	public Set<RespondantNVP> convertToTextFeatures(List<Response> responses, String contentType);
	
}
