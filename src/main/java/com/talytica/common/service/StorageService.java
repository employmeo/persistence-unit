package com.talytica.common.service;

import java.io.File;
import java.util.List;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.employmeo.data.model.Account;

public interface StorageService {

	public String uploadRespondantMediaFile(File media);
	public String uploadAccountMediaFile(File media, Account account);
	public String uploadCoreMediaFile(File media, String folder);	
	public List<S3ObjectSummary> getDirectories(String prefix);
	public void uploadAnalyticsFile(File csv, String filename);
	public String getEnvPath();
	public List<S3ObjectSummary> getDirectoryList(String prefix);
	public String getS3Link(String key);
	public void deleteFile(String key);
	
}