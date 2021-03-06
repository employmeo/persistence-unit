package com.employmeo.data.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.model.Person;
import com.employmeo.data.model.SendGridEmailEvent;
import com.employmeo.data.repository.PersonRepository;
import com.employmeo.data.repository.SendGridEventRepository;

import com.google.common.collect.Sets;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PersonServiceImpl implements PersonService  {

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private SendGridEventRepository sendGridEventRepository;

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
		//Optional<Person> person = personRepository.findById(personId);
		//if(person.isPresent()) {
		//	log.debug("Retrieved for id {} entity {}", personId, person);
		//	return person.get();
		//}
		//return null;
		return personRepository.findOne(personId);
	}

	@Override
	public Person getPersonByAtsId(String atsId) {
		Person person = personRepository.findByAtsId(atsId);
		log.debug("Retrieved for atsid {} entity {}", atsId, person);
		return person;
	}

	@Override
	public List<SendGridEmailEvent> getPersonEmailEvents(Long personId) {
		return sendGridEventRepository.findAllByPersonIdOrderByTimeStampDesc(personId);
	}
	
	@Override
	public List<SendGridEmailEvent> getEmailEvents(String email) {
		return sendGridEventRepository.findAllByEmailIgnoreCaseOrderByTimeStampDesc(email);
	}
}
