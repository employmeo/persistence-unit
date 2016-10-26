package com.employmeo.data.service;

import java.util.Set;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Person;
import com.employmeo.data.repository.PersonRepository;

import jersey.repackaged.com.google.common.collect.Sets;
import lombok.NonNull;

@Service
@Transactional
public class PersonServiceImpl implements PersonService  {
	private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

	@Autowired
	private PersonRepository personRepository;

	@Override
	public Set<Person> getAllPersons() {
		Set<Person> persons = Sets.newHashSet(personRepository.findAll());
		log.debug("Retrieved all {} persons", persons);

		return persons;
	}

	@Override
	public Person save(@NonNull Person person) {
		Person savedPerson = personRepository.save(person);
		log.debug("Saved person {}", person);

		return savedPerson;
	}

	@Override
	public Person getPersonById(@NonNull Long personId) {
		Person person = personRepository.findOne(personId);
		log.debug("Retrieved for id {} entity {}", personId, person);

		return person;
	}
}
