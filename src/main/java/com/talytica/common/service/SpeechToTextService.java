package com.talytica.common.service;

import org.springframework.stereotype.Service;

@Service
public interface SpeechToTextService {
	
	public String translateMedia(String mediaUrl);
	
}
