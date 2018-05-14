package com.employmeo.data.service;

import com.employmeo.data.model.Criterion;

public interface CriterionService {

	Criterion getCriterion(Long criterionId);
	void deleteCriterion(Long criterionId);
	Iterable<Criterion> getAll();
	Criterion save(Criterion criterion);

}
