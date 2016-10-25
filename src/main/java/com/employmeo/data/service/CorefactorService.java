package com.employmeo.data.service;

import com.employmeo.data.model.Corefactor;
import com.employmeo.data.model.identifier.CorefactorId;
 
public interface CorefactorService {

	Iterable<Corefactor> getAllCorefactors();
	Corefactor findCorefactorById(CorefactorId id);
	
}
