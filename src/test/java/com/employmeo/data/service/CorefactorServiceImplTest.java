package com.employmeo.data.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import com.employmeo.data.model.Corefactor;
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
		Long testCorefactorId = 1001L;
		Corefactor testCorefactor = new Corefactor();
		testCorefactor.setId(testCorefactorId);
		testCorefactor.setName("test-name");
		testCorefactor.setDescription("test-description");
		testCorefactor.setLowValue(1.0D);
		testCorefactor.setHighValue(12.0D);
		when(corefactorRepository.findOne(testCorefactorId)).thenReturn(testCorefactor);

		// invoke method under test
		Corefactor resultCorefactor = corefactorService.findCorefactorById(testCorefactorId);

		// assert expectations
		assertNotNull(resultCorefactor);
		assertTrue(testCorefactorId.equals(resultCorefactor.getId()));
		assertEquals("test-name", resultCorefactor.getName());

		verify(corefactorRepository).findOne(testCorefactorId);
		verifyNoMoreInteractions(corefactorRepository);
	}

	@Test(expected = NullPointerException.class)
	public void findCorefactorByIdWithNullId() {
		corefactorService.findCorefactorById(null);
	}

}
