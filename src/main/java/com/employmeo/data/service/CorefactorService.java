package com.employmeo.data.service;

import java.util.List;

import com.employmeo.data.model.Corefactor;

public interface CorefactorService {

	List<Corefactor> getAllCorefactors();
	Corefactor findCorefactorById(Long corefactorId);
	Corefactor getByForeignId(String foreignId);
	Corefactor save(Corefactor corefactor);
	void delete(Long corefactorId);
}
