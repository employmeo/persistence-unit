package com.employmeo.data.service;

import com.employmeo.data.model.Corefactor;
 
public interface CorefactorService {

	Iterable<Corefactor> getAllCorefactors();
	Corefactor findCorefactorById(Long corefactorId);
	
}
