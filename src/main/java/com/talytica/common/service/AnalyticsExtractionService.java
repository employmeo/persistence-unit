package com.talytica.common.service;

import java.io.File;
import java.util.List;

import com.employmeo.data.model.Respondant;

public interface AnalyticsExtractionService {
	
	public File extractAllDataWithOutcome(List<Respondant> respondants, Long targetId) throws Exception;

	public File extractBenchmarkDataWithOutcome(Long benchmarkId, Long targetId) throws Exception;
	
	public File extractPositionDataWithOutcome(Long positionId, Long targetId) throws Exception;

}
