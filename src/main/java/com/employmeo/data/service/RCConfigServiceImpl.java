package com.employmeo.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.ReferenceCheckConfig;
import com.employmeo.data.repository.ReferenceCheckConfigRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RCConfigServiceImpl implements RCConfigService {
	
	@Autowired
	ReferenceCheckConfigRepository rccRepository;
	
	@Override
	public ReferenceCheckConfig getRCConfig(Long rccId) {
		return rccRepository.findById(rccId).get();
		//return rccRepository.findOne(rccId);
	}

	@Override
	public Iterable<ReferenceCheckConfig> getAll() {
		return rccRepository.findAll();
	}

	@Override
	public ReferenceCheckConfig save(ReferenceCheckConfig rcConfig) {
		return rccRepository.save(rcConfig);
	}

	@Override
	public void deleteRCConfig(Long rccId) {
		rccRepository.deleteById(rccId);	
		//rccRepository.delete(rccId);	
	}
	
}
