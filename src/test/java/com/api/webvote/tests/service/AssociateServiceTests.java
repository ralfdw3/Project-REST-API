package com.api.webvote.tests.service;

import com.api.webvote.tests.stubs.AssociateStub;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.exception.NotFoundException;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.repository.AssociateRepository;
import com.api.webvote.v1.service.associate.AssociateService;
import com.api.webvote.v1.service.associate.check.CheckDuplicateCpf;
import com.api.webvote.v1.service.associate.check.CpfValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class AssociateServiceTests {

	@InjectMocks
	private AssociateService associateService;

	@Mock
	private AssociateRepository associateRepository;

	Associate associateDefault = AssociateStub.associateDefault();

	@BeforeEach
	public void setup() {
		openMocks(this);
		when(associateRepository.save(associateDefault)).thenReturn(associateDefault);
		when(associateRepository.findAll()).thenReturn(new ArrayList<Associate>());
		when(associateRepository.findById(1L)).thenReturn(Optional.of(associateDefault));
	}

	@Test
	public void Should_ThrowBadRequestException_When_ValidateIfCpfIsRegisteredInTheDatabase () throws Exception {
		when(associateRepository.findByCpf(associateDefault.getCpf())).thenReturn(associateDefault);
		assertThrows(BadRequestException.class, () -> new CheckDuplicateCpf(associateRepository).check(associateDefault));
	}

	@Test
	public void Should_ReturnOk_When_ValidateIfCpfIsRegisteredInTheDatabase () throws Exception {
		when(associateRepository.findByCpf(associateDefault.getCpf())).thenReturn(null);
		assertDoesNotThrow(() -> new CheckDuplicateCpf(associateRepository).check(associateDefault));
	}

	@Test
	public void Should_ThrowBadRequestException_When_ValidateInvalidCpf () throws Exception {
		assertThrows(BadRequestException.class, () -> new CpfValidator().check(AssociateStub.associateWithInvalidCpf()));
	}

	@Test
	public void Should_ThrowBadRequestException_When_ValidateCpfNumberWithMoreThan11Characters () throws Exception {
		assertThrows(BadRequestException.class, () -> new CpfValidator().check(AssociateStub.associateWithMoreThan11Characters()));
	}

	@Test
	public void Should_ReturnOk_When_ValidateValidCpf() throws Exception {
		assertDoesNotThrow(() -> new CpfValidator().check(associateDefault));
	}
	
	@Test
	public void Should_ReturnOk_When_GetAssociateById () throws Exception {
		assertEquals(ResponseEntity.ok(associateDefault), associateService.get(1L));
		verify(associateRepository, times(1)).findById(any());
	}
	
	@Test
	public void Should_ThrowNotFoundException_When_GetAssociateWithInvalidId () throws Exception {
		when(associateRepository.findById(any())).thenThrow(NotFoundException.class);

		assertThrows(NotFoundException.class, () -> associateService.get(any()));
		verify(associateRepository, times(1)).findById(any());
	}

}
