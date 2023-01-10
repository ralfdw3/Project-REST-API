package com.api.webvote.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.api.webvote.model.Client;
import com.api.webvote.model.Schedule;
import com.api.webvote.model.Vote;
import com.api.webvote.repository.ClientRepository;
import com.api.webvote.repository.ScheduleRepository;
import com.api.webvote.repository.VoteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class VoteControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private VoteController voteController;
	
	@Autowired
	private ClientController clientController;
	
	@Autowired
	private ScheduleController scheduleController;

	@MockBean
	private ClientRepository clientRepository;

	@MockBean
	private ScheduleRepository scheduleRepository;

	@MockBean
	private VoteRepository voteRepository;

	@BeforeEach
	public void setup() {
		standaloneSetup(this.voteController);
	}

	@Test
	public void deveRetornarSucesso_novoVoto() throws Exception {
		
		Client client = new Client("Test Client", "150.000.000-00");
		ResponseEntity<Client> clientExpected = clientController.save(client);

		Schedule schedule = new Schedule("title", 5L);
		ResponseEntity<Schedule> scheduleExpected = scheduleController.newSchedule(schedule);

		Vote vote = new Vote("Sim", clientExpected.getBody().getId(), scheduleExpected.getBody().getId());
		ResponseEntity<Vote> voteExpected = voteController.save(vote);
		
		assertEquals(HttpStatus.OK, voteExpected.getStatusCode());
	}

	@Test
	public void deveRetornarFalha_votoDiferenteSimNao() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		Client client = new Client("Test Client", "000.000.000-00");
		clientRepository.save(client);

		Schedule schedule = new Schedule("title", 5L);
		scheduleRepository.save(schedule);

		Vote vote = new Vote("Sim", client.getId(), schedule.getId());

		mockMvc.perform(post("/api/vote/save").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(vote))).andExpect(status().isBadRequest());
	}

	@Test
	public void deveRetornarFalha_pautaNaoEncontrada() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		Client client = new Client("Test Client", "000.000.000-00");
		clientRepository.save(client);

		Vote vote = new Vote("Sim", client.getId(), 999999L);

		mockMvc.perform(post("/api/vote/save").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(vote))).andExpect(status().isNotFound());
	}

	@Test
	public void deveRetornarFalha_clienteJaVotouNaPauta() throws Exception 
	{
		Client testClient = new Client("Test Client", "000.000.000-00");
		testClient.setId(1L);
	
		Schedule testSchedule = new Schedule("Test Schedule", 1L);
		testSchedule.setId( 1L );
	
		Vote testVote = new Vote("Sim", testClient.getId(), testSchedule.getId());
		testVote.setId( 1L );
	
		when(clientRepository.save(testClient)).thenReturn( testClient );
		when(scheduleRepository.save(testSchedule)).thenReturn( testSchedule );
		when(voteRepository.save(testVote)).thenReturn( testVote );

		// cria uma requisição POST para a API passando o mesmo cliente e pauta de teste
		MvcResult result = mockMvc
				.perform(post("/api/vote/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString( testVote )))
				.andExpect(status().isBadRequest()).andReturn();
	}

	public static String asJsonString(final Object obj) 
	{
		try 
		{
			return new ObjectMapper().writeValueAsString(obj);
		}
		catch (JsonProcessingException e) 
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	public void deveRetornarFalha_tempoPautaExpirado() throws Exception {

		// Configurar o schedule para que o tempo de votação tenha expirado

		Long id = 15L;
		String title = "title";
		Long clientId = 3L;
		int durationTime = 10;
		Timestamp startTime = Timestamp.valueOf(LocalDateTime.now());
		Timestamp endTime = Timestamp.valueOf(startTime.toLocalDateTime().plusMinutes(5));
		int yesVotesCount = 3;
		int noVotesCount = 2;

		Schedule expiredSchedule = new Schedule(id, title, clientId, durationTime, startTime, endTime, yesVotesCount,
				noVotesCount);

		scheduleRepository.save(expiredSchedule);

		Vote testVote = new Vote("Sim", clientId, expiredSchedule.getId());

		// Enviar requisição e verificar se a resposta é "Bad Request"
		MvcResult result = mockMvc
				.perform(post("/api/vote/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(testVote)))
				.andExpect(status().isBadRequest()).andReturn();

		// Verificar se o motivo da falha foi o término da sessão de votação
		Assertions.assertEquals("O tempo de votação da pauta já expirou", result.getResponse().getErrorMessage());
	}

}