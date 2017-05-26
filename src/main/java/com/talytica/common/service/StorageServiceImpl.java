package com.talytica.common.service;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.employmeo.data.model.Account;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService{
	
    @Autowired
    private AmazonS3Client s3Client;
    
	@Value("${com.talytica.media.s3bucket:talytica}")
	private String s3BucketName;
	
	@Value("${com.talytica.media.environment:dev/}")
	private String environmentPath;
	
	@Value("respondants/")
	private String respondantMediaFolder;
	
	@Value("accounts/")
	private String accountMediaFolder;
	
	@Value("media/")
	private String coreMediaFolder;
	
	private HashMap<String,List<S3ObjectSummary>> directoryLists;
	
	@Override
	public String uploadRespondantMediaFile(File media) {
		String key = environmentPath + respondantMediaFolder + Instant.now().getEpochSecond() + "_" + media.getName();
		String url = "https://s3.amazonaws.com/" + s3BucketName + "/" + key;
		try {	
	        s3Client.putObject(new PutObjectRequest(s3BucketName, key, media)
	        			.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (Exception e) {
			log.error("Failed to save file: {} to url: {}", media.getName());
			return null; 
		}
		return url;
	}
	
	@Override
	public String uploadAccountMediaFile(File media, Account account) {
		String key = environmentPath + accountMediaFolder + account.getId() + "/"+ Instant.now().getEpochSecond() + "_" + media.getName();
		String url = "https://s3.amazonaws.com/" + s3BucketName + "/" + key;
		try {	
	        s3Client.putObject(new PutObjectRequest(s3BucketName, key, media)
	        			.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (Exception e) {
			log.error("Failed to save file: {} to url: {}", media.getName());
			return null; 
		}
		return url;
	}
	
	@Override
	public String uploadCoreMediaFile(File media, String folder) {
		String key = coreMediaFolder + folder + media.getName();
		String url = "https://s3.amazonaws.com/" + s3BucketName + "/" + key;
		try {	
	        s3Client.putObject(new PutObjectRequest(s3BucketName, key, media)
	        			.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (Exception e) {
			log.error("Failed to save file: {} to url: {}", media.getName());
			return null; 
		}
		return url;
	}
	
	@Override
	public List<S3ObjectSummary> getDirectories(String prefix) {
		
		if (this.directoryLists.containsKey(prefix)) return this.directoryLists.get(prefix);	
		List<S3ObjectSummary> folders = new ArrayList<S3ObjectSummary>();
		ObjectListing objectListing = s3Client.listObjects(new ListObjectsRequest().withBucketName(s3BucketName).withPrefix(prefix));
		
		for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
			if (summary.getSize() == 0) folders.add(summary);
		}
		this.directoryLists.put(prefix, folders);
        return folders;
	}
	
	@PostConstruct
	private void initializeDirectories() {
		this.directoryLists = new HashMap<String,List<S3ObjectSummary>>();
	}
	
}
