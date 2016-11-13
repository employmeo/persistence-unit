package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.Corefactor;

public interface CorefactorService {

	Set<Corefactor> getAllCorefactors();
	Corefactor findCorefactorById(Long corefactorId);
	Corefactor getByForeignId(String foreignId);
	Corefactor save(Corefactor corefactor);

}
