package com.employmeo.data.service;

import java.util.*;

import com.employmeo.data.model.*;

import lombok.NonNull;

public interface PopulationService {

	Population getPopulationById(@NonNull Long populationId);

	Set<Population> getBenchmarkPopulations(@NonNull Long benchmarkId);
	
	Population save(@NonNull Population population);
	
	PopulationScore save(@NonNull PopulationScore ps);

	Set<PopulationScore> getScoresByPopulationId(@NonNull Long populationId);

}