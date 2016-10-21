package com.employmeo.data.dao.hibernate;

import static org.junit.Assert.*;

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
import com.employmeo.data.dao.CorefactorDao;
import com.employmeo.data.model.Corefactor;
import com.employmeo.data.model.identifier.CorefactorId;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PersistenceConfiguration.class}, loader=AnnotationConfigContextLoader.class)
@Transactional
public class CorefactorDaoImplIntegrationTest {

	@Autowired
	private CorefactorDao corefactorDao;

	@Test
	@Sql(scripts = "/sql/CorefactorDaoImplIntegrationTest.sql", config = @SqlConfig(commentPrefix = "--"))
	public void findById() {
		Corefactor corefactor = corefactorDao.findById(CorefactorId.of(1001L));
		
		assertNotNull("Corefactor is null", corefactor);
		assertTrue("Corefactor ids not as expected", 1001L == corefactor.getIdValue());
		assertNotNull("Corefactor name is null", corefactor.getName());
		assertEquals("Corefactor name not as expected", "test-humility", corefactor.getName());
	}

}
