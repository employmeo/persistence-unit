package com.employmeo.data.service;

import java.util.List;

import com.employmeo.data.model.Corefactor;
import com.employmeo.data.model.identifier.CorefactorId;
 
public interface CorefactorService {

	List<Corefactor> getAllCorefactors();
	Corefactor findCorefactorById(CorefactorId id);
	
}
