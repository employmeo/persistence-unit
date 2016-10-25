package com.employmeo.data.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.employmeo.data.model.Corefactor;
import com.employmeo.data.model.identifier.CorefactorId;
import com.employmeo.data.repository.CorefactorRepository;

@RunWith(MockitoJUnitRunner.class)
public class CorefactorServiceImplTest {
	@Mock
	CorefactorRepository corefactorRepository;

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
		when(corefactorRepository.findOne(testCorefactorId.getLongValue())).thenReturn(testCorefactor);
		
		// invoke method under test
		Corefactor resultCorefactor = corefactorService.findCorefactorById(testCorefactorId);
		
		// assert expectations
		assertNotNull(resultCorefactor);
		assertTrue(testCorefactorId.equals(resultCorefactor.getId()));
		assertEquals("test-name", resultCorefactor.getName());
		
		verify(corefactorRepository).findOne(testCorefactorId.getLongValue());
		verifyNoMoreInteractions(corefactorRepository);
	}
	
	@Test(expected = NullPointerException.class)
	public void findCorefactorByIdWithNullId() {
		corefactorService.findCorefactorById(null);
	}

}
