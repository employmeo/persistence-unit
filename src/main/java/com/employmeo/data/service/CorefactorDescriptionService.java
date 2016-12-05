package com.employmeo.data.service;

import java.util.List;

import com.employmeo.data.model.CorefactorDescription;

public interface CorefactorDescriptionService {

	List<CorefactorDescription> getAll();
	CorefactorDescription getById(Long id);
	CorefactorDescription save(CorefactorDescription corefactorDescription);
	void delete(Long id);

}
