package com.talytica.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.employmeo.data.model.Corefactor;
import com.employmeo.data.model.Outcome;
import com.employmeo.data.model.Respondant;
import com.employmeo.data.model.RespondantNVP;
import com.employmeo.data.model.RespondantScore;
import com.employmeo.data.model.Response;
import com.employmeo.data.repository.PredictionTargetRepository;
import com.employmeo.data.service.AccountService;
import com.employmeo.data.service.CorefactorService;
import com.employmeo.data.service.RespondantService;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;


@Slf4j
@Service
public class AnalyticsExtractionServiceImpl implements AnalyticsExtractionService {
	
	private static final String DELIMITER = ",";
	private static final String NEWLINE = "\n";

	@Value("${com.talytica.temp.directory:c://users//sri//temp//}")
	String tempDir;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	RespondantService respondantService;

	@Autowired
	PredictionTargetRepository predictionTargetRepository;
	
	@Autowired
	CorefactorService corefactorService;
	
	@Override
	public File extractAllDataWithOutcome(List<Respondant> respondants, Long targetId) throws Exception {

		String csvFileName = tempDir+"export-"+(new Date()).getTime()+".csv";
	    File csvFile = new File(csvFileName);
	    FileWriter fileWriter = new FileWriter(csvFile);
	    
	    log.debug("writing file with {} respondants", respondants.size());
	    String header = null;
	    List<Long> headers = new ArrayList<Long>();
	
	    Collections.sort(respondants, (r2,r1) -> Integer.valueOf(r1.getRespondantScores().size()).compareTo(r2.getRespondantScores().size()));
	    
	    for (Respondant respondant : respondants) {
	       	Set<RespondantScore> scores = respondant.getRespondantScores();
	       	Set<RespondantNVP> nvps = respondantService.getNVPsForRespondant(respondant.getId());
	       	Set<Outcome> outcomes = respondantService.getOutcomesForRespondant(respondant.getId());
	       	if (header == null) {
	       		StringBuffer sbHeader = new StringBuffer();
	       		sbHeader.append("respondant_id");
	       		for (RespondantScore score : scores) {
	       			Corefactor corefactor = corefactorService.findCorefactorById(score.getId().getCorefactorId());
	       			sbHeader.append(DELIMITER);
	       			sbHeader.append(corefactor.getName());
	       			headers.add(corefactor.getId());
	       		}
	    		sbHeader.append(DELIMITER);
	    		sbHeader.append("FREETEXT"); // added this colmnn to slap on all words from NVP free-text.
	    		sbHeader.append(DELIMITER);
	    		sbHeader.append("OUTCOME");
	    		sbHeader.append(NEWLINE);
	    		header = sbHeader.toString();
	           	fileWriter.append(header);        		
	       	}
	       	StringBuffer lineItem = new StringBuffer();
	       	lineItem.append(respondant.getId());
	       	for (Long cfid : headers) {
	    		lineItem.append(DELIMITER);
	    		lineItem.append(findCorefactorValue(cfid, scores));
	       	}
	       	lineItem.append(DELIMITER);
	       	StringBuffer freeText = new StringBuffer();
	       	for (RespondantNVP nvp : nvps) {
	       		freeText.append(nvp.getValue());
	       	}
	        Set<Response> responses = respondantService.getResponsesById(respondant.getId());
	       	for (Response resp : responses) {
	       		if (null != resp.getResponseText()) freeText.append(resp.getResponseText());
	       	}
			lineItem.append(StringEscapeUtils.escapeCsv(freeText.toString()));
			lineItem.append(DELIMITER);
			lineItem.append(findOutcomeValue(targetId, outcomes));
			lineItem.append(NEWLINE);
			fileWriter.append(lineItem.toString());
			fileWriter.flush();
	    }
	    fileWriter.close();
		return csvFile;
	}

	@Override
	public File extractBenchmarkDataWithOutcome(Long benchmarkId, Long targetId) throws Exception {
	    //Benchmark benchmark = accountService.getBenchmarkById(benchmarkId);
	    List<Respondant> respondants = Lists.newArrayList(respondantService.getCompletedForBenchmarkId(benchmarkId));
		return extractAllDataWithOutcome(respondants, targetId);
	}

	@Override
	public File extractPositionDataWithOutcome(Long positionId, Long targetId) throws Exception {

		List<Respondant> respondants = respondantService.getScoredApplicantsByPosition(positionId);
		return extractAllDataWithOutcome(respondants, targetId);
	}
	

    private String findCorefactorValue(Long id, Set<RespondantScore> scores) {
    	Optional<RespondantScore> value = scores.stream().filter(cfs -> id.equals(cfs.getId().getCorefactorId())).findFirst();
    	if (value.isPresent()) {
    		return value.get().getValue().toString();
    	}
    	return "";
    }
    
    private String findOutcomeValue(Long id, Set<Outcome> outcomes) {
    	Optional<Outcome> value = outcomes.stream().filter(outcome -> id.equals(outcome.getPredictionTarget().getPredictionTargetId())).findFirst();
    	if (value.isPresent()) {
    		if (value.get().getValue()) return "1";
    		return "0";
    	}
    	return "";
    }
	
}
