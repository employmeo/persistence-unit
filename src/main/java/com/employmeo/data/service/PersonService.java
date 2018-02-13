package com.employmeo.data.service;

import java.util.List;
import java.util.Set;

import com.employmeo.data.model.Person;
import com.employmeo.data.model.SendGridEmailEvent;

import lombok.NonNull;

public interface PersonService {

	Set<Person> getAllPersons();
	
	Person getPersonByAtsId(String atsId);

	Person save(@NonNull Person person);

	Person getPersonById(@NonNull Long personId);
	
	List<SendGridEmailEvent> getEmailEvents(@NonNull Long personId);

}