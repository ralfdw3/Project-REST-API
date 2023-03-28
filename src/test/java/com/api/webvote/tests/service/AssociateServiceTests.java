package com.api.webvote.tests.service;

import com.api.webvote.tests.stubs.AssociateStub;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.exception.NotFoundException;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.repository.AssociateRepository;
import com.api.webvote.v1.service.associate.AssociateService;
import com.api.webvote.v1.service.check.CheckDuplicateCpf;
import com.api.webvote.v1.service.check.CpfValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
	public void deveRetornarSucesso_aoCadastrarNovoAssociado() throws Exception {
		when(associateRepository.findByCpf(associateDefault.getCpf())).thenReturn(null);

		assertEquals(HttpStatus.OK, associateService.save(associateDefault).getStatusCode());
		verify(associateRepository, times(1)).save(any(Associate.class));
	}

	@Test
	public void deveRetornarFalha_aoValidarCPFJaEstaCadastrado() throws Exception {
		when(associateRepository.findByCpf(associateDefault.getCpf())).thenReturn(associateDefault);
		assertThrows(BadRequestException.class, () -> CheckDuplicateCpf.validate(associateDefault.getCpf(), associateRepository));
	}

	@Test
	public void deveRetornarSucesso_aoValidarCPFJaEstaCadastrado() throws Exception {
		when(associateRepository.findByCpf(associateDefault.getCpf())).thenReturn(null);
		assertDoesNotThrow(() -> CheckDuplicateCpf.validate(associateDefault.getCpf(), associateRepository));
	}

	@Test
	public void deveRetornarFalha_aoValidarCPFInvalido() throws Exception {
		assertThrows(BadRequestException.class, () -> CpfValidator.validate("000.000.000-05"));
	}

	@Test
	public void deveRetornarFalha_aoValidarCPFComMaisDe11Digitos() throws Exception {
		assertThrows(BadRequestException.class, () -> CpfValidator.validate("035.592.500-150"));
	}

	@Test
	public void deveRetornarSucesso_aoValidarCPFValido() throws Exception {
		assertDoesNotThrow(() -> CpfValidator.validate("000.000.000-00"));
	}
	
	@Test
	public void deveRetornarSucesso_aoBuscarUmAssociado() throws Exception {
		assertEquals(ResponseEntity.ok(associateDefault), associateService.get(1L));
		verify(associateRepository, times(1)).findById(any());
	}
	
	@Test
	public void deveRetornarFalha_aoBuscarAssociadoPorIdInvalido() throws Exception {
		when(associateRepository.findById(any())).thenThrow(NotFoundException.class);

		assertThrows(NotFoundException.class, () -> associateService.get(any()));
		verify(associateRepository, times(1)).findById(any());
	}

}
