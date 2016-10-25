package com.employmeo.data.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.employmeo.data.config.PersistenceConfiguration;
import com.employmeo.data.model.Corefactor;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PersistenceConfiguration.class}, loader=AnnotationConfigContextLoader.class)
@Transactional
public class CorefactorRepositoryIntegrationTest {

	@Autowired
	private CorefactorRepository corefactorRepository;
	
	@Test
	@Sql(scripts = "/sql/CorefactorRepositoryIntegrationTest.sql", config = @SqlConfig(commentPrefix = "--"))
	public void findById() {
		Corefactor corefactor =  corefactorRepository.findOne(1001L);
		
		assertNotNull("Corefactor is null", corefactor);
		assertTrue("Corefactor ids not as expected", 1001L == corefactor.getId());
		assertNotNull("Corefactor name is null", corefactor.getName());
		assertEquals("Corefactor name not as expected", "test-humility", corefactor.getName());
	}	
}
