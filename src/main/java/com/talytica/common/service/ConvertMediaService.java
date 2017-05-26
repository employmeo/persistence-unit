package com.talytica.common.service;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioFormat.Encoding;

public interface ConvertMediaService {
	
	public File convertUrl (URL mediaUrl, Encoding audioEncoding, String filename);
	
}
