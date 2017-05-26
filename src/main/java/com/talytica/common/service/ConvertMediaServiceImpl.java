package com.talytica.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


@Slf4j
@Service
public class ConvertMediaServiceImpl implements ConvertMediaService {
		
	@Value("${com.talytica.temp.directory:c://users//sri//temp//}")
	String TEMP_PATH;
	
	private final int BIT_RATE = 16;

	
	@Override
	public File convertUrl (URL mediaUrl, Encoding audioEncoding, String filename) {
		File file = null;
        log.debug("Attempting to convert {}", mediaUrl);
		try {

			log.debug("Creating file");	
			file = new File(TEMP_PATH + filename);  
			
			AudioFileFormat sourceFileFormat = AudioSystem.getAudioFileFormat(mediaUrl);
			if (AudioFileFormat.Type.WAVE == sourceFileFormat.getType()) {
				log.debug("Same encoding: {}", audioEncoding);
				Files.copy(mediaUrl.openStream(), file.toPath());
			} else {
				log.debug("need to convert {} into WAVE file", sourceFileFormat.getType());
	
				InputStream in = mediaUrl.openStream();
		        ByteArrayOutputStream bao = new ByteArrayOutputStream();
		        byte[] buff = new byte[8000];
	            int bytesRead = 0;
		        while((bytesRead = in.read(buff)) != -1) {
		             bao.write(buff, 0, bytesRead);
		        }
		        byte[] data = bao.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(data);
				AudioInputStream sourceAis = AudioSystem.getAudioInputStream(bais);
				AudioFormat sourceFormat = sourceAis.getFormat();
				
				log.debug("determined encoding of {} to be {}", mediaUrl, sourceFormat.getEncoding());	
				AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sourceFormat.getSampleRate(), BIT_RATE, sourceFormat.getChannels(), sourceFormat.getChannels()*2, sourceFormat.getSampleRate(), false);
		        AudioFormat toFormat = new AudioFormat(audioEncoding, sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(), sourceFormat.getFrameSize(), sourceFormat.getFrameRate(), sourceFormat.isBigEndian());
	
	            AudioInputStream interimAis = AudioSystem.getAudioInputStream(convertFormat, sourceAis);
	            AudioInputStream convertedAis = AudioSystem.getAudioInputStream(toFormat, interimAis);
	            
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
				log.debug("Created BAOS");
	            
	            byte [] buffer = new byte[8192];
	            while(true){
	            	int readCount = convertedAis.read(buffer, 0, buffer.length);
	                if(readCount == -1)	break;
	                baos.write(buffer, 0, readCount);
	            }
	            FileOutputStream fos = new FileOutputStream (file);
	            baos.writeTo(fos);
				log.debug("Wrote file");
				fos.close();
				fos.flush();
			}
        } catch (Exception e) {
        	log.error("File conversion of {} failed: {}", mediaUrl, e.getMessage());
        	e.printStackTrace();
        }
		return file;

	}
	
}
