package com.api.webvote.tests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.exception.NotFoundException;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.repository.AssociateRepository;
import com.api.webvote.v1.service.check.CheckDuplicateCpf;
import com.api.webvote.v1.service.associate.AssociateService;
import com.api.webvote.v1.service.check.CpfValidator;

public class AssociateServiceTests {

	@InjectMocks
	private AssociateService clientService;

	@Mock
	private AssociateRepository associateRepository;

	@BeforeEach
	public void conditions() {
		openMocks(this);
		when(associateRepository.save(clientMock)).thenReturn(clientMock);
		when(associateRepository.findAll()).thenReturn(clients);
		when(associateRepository.findById(1L)).thenReturn(Optional.of(clientMock));
		when(associateRepository.findById(2L)).thenThrow(NotFoundException.class);
	}

	// Variáveis mocks
	Associate clientMock = new Associate(1L, "Ralf Drehmer Wink", "000.000.000-00");
	List<Associate> clients = new ArrayList<Associate>();
	
	@Test
	public void deveRetornarSucesso_novoCliente() throws Exception {
		ResponseEntity<Associate> response = clientService.save(clientMock);

		assertEquals(response, ResponseEntity.ok().build());
		verify(associateRepository, times(1)).save(any(Associate.class));
	}

	@Test
	public void deveRetornarFalha_cpfJaCadastrado() throws Exception {
		clients.add(clientMock);

		ResponseEntity<Associate> response = clientService.save(clientMock);

		assertEquals(ResponseEntity.badRequest().build(), response);
		assertThrows(BadRequestException.class, () -> CheckDuplicateCpf.validate(clientMock));
		verify(associateRepository, times(0)).save(any(Associate.class));
	}

	@Test
	public void deveRetornarFalha_cpfInvalido() throws Exception {
		Associate clientMock = new Associate(1L, "Ralf Drehmer Wink", "035.592.500-15");

		ResponseEntity<Associate> response = clientService.save(clientMock);

		assertEquals(ResponseEntity.badRequest().build(), response);
		assertThrows(BadRequestException.class, () -> CpfValidator.validate(clientMock));
		verify(associateRepository, times(0)).save(any(Associate.class));
	}
	
	@Test
	public void deveRetornarSucesso_QuandoBuscarUmCliente() throws Exception {

		ResponseEntity<Associate> response = clientService.get(1L);
		assertEquals(response, ResponseEntity.ok(clientMock));
		verify(associateRepository, times(1)).findById(any());
	}
	
	@Test
	public void deveRetornarFalha_QuandoBuscarOResultadoDeUmaPauta() throws Exception {

		assertThrows(NotFoundException.class, () -> clientService.get(2L));
		verify(associateRepository, times(1)).findById(any());
	}

}
