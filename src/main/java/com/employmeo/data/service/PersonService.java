package com.employmeo.data.service;

import java.util.Set;

import com.employmeo.data.model.Person;

import lombok.NonNull;

public interface PersonService {

	Set<Person> getAllPersons();

	Person save(Person person);

	Person getPersonById(Long personId);

}