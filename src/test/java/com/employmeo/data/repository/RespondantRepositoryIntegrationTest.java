package com.employmeo.data.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.config.PersistenceConfiguration;
import com.employmeo.data.model.Respondant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PersistenceConfiguration.class}, loader=AnnotationConfigContextLoader.class)
@Transactional
@Ignore
public class RespondantRepositoryIntegrationTest {

	@Autowired
	private RespondantRepository respondantRepository;

	@Test
	//@Sql(scripts = "/sql/CorefactorRepositoryIntegrationTest.sql", config = @SqlConfig(commentPrefix = "--"))
	public void findById() {
		UUID uuid = UUID.fromString("659f8062-fdd4-4bda-a954-c462e4252608");
		Long id = 3574L;
		Long personId = 3530L;
		Respondant respondant =  respondantRepository.findOne(id);

		log.debug("Respondant: {}", respondant);
		assertNotNull("Respondant is null", respondant);
		assertTrue("Respondant id not as expected", id.equals(respondant.getId()));
		assertTrue("Respondant uuid not as expected", uuid.equals(respondant.getRespondantUuid()));
		assertTrue("Respondant person id not as expected", personId.equals(respondant.getPersonId()));
	}

	@Test
	//@Sql(scripts = "/sql/CorefactorRepositoryIntegrationTest.sql", config = @SqlConfig(commentPrefix = "--"))
	public void findByRespondantUuid() {
		UUID uuid = UUID.fromString("659f8062-fdd4-4bda-a954-c462e4252608");
		Long id = 3574L;
		Long personId = 3530L;
		Respondant respondant =  respondantRepository.findByRespondantUuid(uuid);

		log.debug("Respondant: {}", respondant);
		assertNotNull("Respondant is null", respondant);
		assertTrue("Respondant id not as expected", id.equals(respondant.getId()));
		assertTrue("Respondant uuid not as expected", uuid.equals(respondant.getRespondantUuid()));
		assertTrue("Respondant person id not as expected", personId.equals(respondant.getPersonId()));
	}

	@Test
	public void findAllByAccountId() {
		Long accountId = 1L;
		Pageable  pageRequest = new PageRequest(0, 5, Sort.Direction.DESC, "id");
		Page<Respondant> respondants =  respondantRepository.findAllByAccountId(accountId, pageRequest);
		log.debug("Account filtered Respondants: {}", respondants);

		assertNotNull("Respondant is null", respondants);
		assertTrue(!respondants.getContent().isEmpty());


		log.debug("Total respondants fetched for accountId " + accountId + ": " + respondants.getSize());
	}

	@Test
	public void findAllByAccountIdAndLocationId() {
		Long accountId = 1L;
		Long locationId = 1L;
		Pageable  pageRequest = new PageRequest(0, 5, Sort.Direction.DESC, "id");

		Page<Respondant> respondants =  respondantRepository.findAllByAccountIdAndLocationId(accountId, locationId, pageRequest);
		log.debug("Account and Location filtered Respondants: {}", respondants);

		assertNotNull("Respondant is null", respondants);
		assertTrue(!respondants.getContent().isEmpty());

		log.debug("Total respondants fetched for accountId " + accountId + " and locationId " + locationId + ": " + respondants.getSize());
	}

	@Test
	public void findAllByAccountIdAndPositionId() {
		Long accountId = 1L;
		Long positionId = 19L;
		Pageable  pageRequest = new PageRequest(0, 5, Sort.Direction.DESC, "id");

		Page<Respondant> respondants =  respondantRepository.findAllByAccountIdAndPositionId(accountId, positionId, pageRequest);
		log.debug("Account and Position filtered Respondants: {}", respondants);

		assertNotNull("Respondant is null", respondants);
		assertTrue(!respondants.getContent().isEmpty());

		log.debug("Total respondants fetched for accountId " + accountId + " and positionId " + positionId + ": " + respondants.getSize());
	}

	@Test
	public void findAllByAccountIdAndRespondantStatusIn() {
		Long accountId = 1L;
		List<Integer> statuses = Arrays.asList(Respondant.STATUS_COMPLETED, Respondant.STATUS_SCORED, Respondant.STATUS_PREDICTED);
		Pageable  pageRequest = new PageRequest(0, 5, Sort.Direction.DESC, "id");

		Page<Respondant> respondants =  respondantRepository.findAllByAccountIdAndRespondantStatusIn(accountId, statuses, pageRequest);
		log.debug("Account and Status filtered Respondants: {}", respondants);

		assertNotNull("Respondant is null", respondants);
		assertTrue(!respondants.getContent().isEmpty());

		log.debug("Total respondants fetched for accountId " + accountId + " and statuses " + statuses + ": " + respondants.getSize());
	}
}
