package com.employmeo.data.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Criterion;
import com.employmeo.data.repository.CriterionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CriterionServiceImpl implements CriterionService {
	
	@Autowired
	CriterionRepository criterionRepository;
	
	@Override
	public Criterion getCriterion(Long criterionId) {
		//Optional<Criterion> criterion = criterionRepository.findById(criterionId);
		//if (criterion.isPresent()) return criterion.get();
		//return null;
		return criterionRepository.findOne(criterionId);
	}

	@Override
	public void deleteCriterion(Long criterionId) {
		//criterionRepository.deleteById(criterionId);
		criterionRepository.delete(criterionId);
	}

	@Override
	public Iterable<Criterion> getAll() {
		return criterionRepository.findAll();
	}

	@Override
	public Criterion save(Criterion criterion) {
		return criterionRepository.save(criterion);
	}



}
