package com.employmeo.data.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.employmeo.data.dao.CorefactorDao;
import com.employmeo.data.model.Corefactor;
import com.employmeo.data.model.identifier.CorefactorId;

@RunWith(MockitoJUnitRunner.class)
public class CorefactorServiceImplTest {
	@Mock
	CorefactorDao corefactorDao;

	@InjectMocks
	CorefactorServiceImpl corefactorService;
	
	@Test
	public void findCorefactorByIdWithValidId() {
		// setup data and expectations
		CorefactorId testCorefactorId = CorefactorId.of(1001L);
		Corefactor testCorefactor = Corefactor.builder()
				.idValue(1001L)
				.name("test-name")
				.description("test-description")
				.lowValue(1.0D)
				.highValue(12.0D)
				.build();
		when(corefactorDao.findById(testCorefactorId)).thenReturn(testCorefactor);
		
		// invoke method under test
		Corefactor resultCorefactor = corefactorService.findCorefactorById(testCorefactorId);
		
		// assert expectations
		assertNotNull(resultCorefactor);
		assertTrue(testCorefactorId.equals(resultCorefactor.getId()));
		assertEquals("test-name", resultCorefactor.getName());
		
		verify(corefactorDao).findById(testCorefactorId);
		verifyNoMoreInteractions(corefactorDao);
	}
	
	@Test(expected = NullPointerException.class)
	public void findCorefactorByIdWithNullId() {
		corefactorService.findCorefactorById(null);
	}

}
