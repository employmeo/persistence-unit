package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.Person;

import lombok.NonNull;

public interface PersonService {

	Set<Person> getAllPersons();
	
	Person getPersonByAtsId(String atsId);

	Person save(@NonNull Person person);

	Person getPersonById(@NonNull Long personId);

}