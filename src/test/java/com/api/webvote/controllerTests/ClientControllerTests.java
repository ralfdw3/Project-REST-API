package com.api.webvote.controllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.api.webvote.controller.ClientController;
import com.api.webvote.model.Client;
import com.api.webvote.repository.ClientRepository;
import com.api.webvote.repository.ScheduleRepository;
import com.api.webvote.repository.VoteRepository;

@WebMvcTest
public class ClientControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ClientController clientController;

	@MockBean
	private ClientRepository clientRepository;

	@MockBean
	private ScheduleRepository scheduleRepository;

	@MockBean
	private VoteRepository voteRepository;

	Client clientMock = new Client(1L, "Client name", "000.000.000-00");

	@Test
	public void deveRetornarSucesso_QuandoCriarCliente() throws Exception {
		ResponseEntity<Client> clientExpected = clientController.save(clientMock);

		assertEquals(HttpStatus.OK, clientExpected.getStatusCode());
	}
	
	@Test
	public void deveRetornarFalha_QuandoCpfForInvalido() throws Exception {
		Client clientMock = new Client(1L, "Nome do Sujeito", "555.666.777-88");

		ResponseEntity<Client> clientExpected = clientController.save(clientMock);

		assertEquals(HttpStatus.BAD_REQUEST, clientExpected.getStatusCode());
	}
	
	@Test
	public void deveRetornarFalha_QuandoCpfForDuplicado() throws Exception {
		List <Client> clientes = new ArrayList<>();
		clientes.add(clientMock);
		
		when(clientRepository.findAll()).thenReturn(clientes);
		
		ResponseEntity<Client> clientExpected = clientController.save(clientMock);

		assertEquals(HttpStatus.BAD_REQUEST, clientExpected.getStatusCode());
	}

	@Test
	public void deveRetornarSucesso_QuandoBuscarCliente() throws Exception {
		when(clientRepository.findById(1L)).thenReturn(Optional.of(clientMock));

		mockMvc.perform(get("/api/client/{id}", clientMock.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

		verify(clientRepository).findById(clientMock.getId());
	}
	
	@Test
	public void deveRetornarFalha_QuandoClienteNaoForEncontrado() throws Exception {
		mockMvc.perform(get("/api/client/{id}", 999999)).andExpect(status().isNotFound());
	}

}
