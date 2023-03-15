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
import com.api.webvote.v1.model.Client;
import com.api.webvote.v1.repository.ClientRepository;
import com.api.webvote.v1.service.client.CheckDuplicateCpf;
import com.api.webvote.v1.service.client.ClientService;
import com.api.webvote.v1.service.client.CpfValidator;

public class ClientServiceTests {

	@InjectMocks
	private ClientService clientService;

	@Mock
	private ClientRepository clientRepository;

	@BeforeEach
	public void conditions() {
		openMocks(this);
		when(clientRepository.save(clientMock)).thenReturn(clientMock);
		when(clientRepository.findAll()).thenReturn(clients);
		when(clientRepository.findById(1L)).thenReturn(Optional.of(clientMock));
		when(clientRepository.findById(2L)).thenThrow(NotFoundException.class);
	}

	// Variáveis mocks
	Client clientMock = new Client(1L, "Ralf Drehmer Wink", "000.000.000-00");
	List<Client> clients = new ArrayList<Client>();
	
	@Test
	public void deveRetornarSucesso_novoCliente() throws Exception {
		ResponseEntity<Client> response = clientService.save(clientMock);

		assertEquals(response, ResponseEntity.ok().build());
		verify(clientRepository, times(1)).save(any(Client.class));
	}

	@Test
	public void deveRetornarFalha_cpfJaCadastrado() throws Exception {
		clients.add(clientMock);

		ResponseEntity<Client> response = clientService.save(clientMock);

		assertEquals(ResponseEntity.badRequest().build(), response);
		assertThrows(BadRequestException.class, () -> CheckDuplicateCpf.validate(clientMock, clients));
		verify(clientRepository, times(0)).save(any(Client.class));
	}

	@Test
	public void deveRetornarFalha_cpfInvalido() throws Exception {
		Client clientMock = new Client(1L, "Ralf Drehmer Wink", "035.592.500-15");

		ResponseEntity<Client> response = clientService.save(clientMock);

		assertEquals(ResponseEntity.badRequest().build(), response);
		assertThrows(BadRequestException.class, () -> CpfValidator.validate(clientMock));
		verify(clientRepository, times(0)).save(any(Client.class));
	}
	
	@Test
	public void deveRetornarSucesso_QuandoBuscarUmCliente() throws Exception {

		ResponseEntity<Client> response = clientService.get(1L);
		assertEquals(response, ResponseEntity.ok(clientMock));
		verify(clientRepository, times(1)).findById(any());
	}
	
	@Test
	public void deveRetornarFalha_QuandoBuscarOResultadoDeUmaPauta() throws Exception {

		assertThrows(NotFoundException.class, () -> clientService.get(2L));
		verify(clientRepository, times(1)).findById(any());
	}

}
