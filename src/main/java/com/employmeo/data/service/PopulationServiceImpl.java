package com.employmeo.data.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.*;
import com.employmeo.data.repository.PopulationRepository;
import com.employmeo.data.repository.PopulationScoreRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PopulationServiceImpl implements PopulationService {
	
	@Autowired
	PopulationRepository populationRepository;
	
	@Autowired
	PopulationScoreRepository populationScoreRepository;
	
	
	@Override
	public Population getPopulationById(Long populationId) {
		return populationRepository.findOne(populationId);
	}

	@Override
	public Set<Population> getBenchmarkPopulations(Long benchmarkId) {
		return populationRepository.findAllByBenchmarkId(benchmarkId);
	}

	@Override
	public Population save(Population population) {
		return populationRepository.save(population);
	}

	@Override
	public PopulationScore save(PopulationScore ps) {
		return populationScoreRepository.save(ps);
	}

	@Override
	public Set<PopulationScore> getScoresByPopulationId(Long populationId) {
		return populationScoreRepository.findAllByPopulationId(populationId);
	}



}