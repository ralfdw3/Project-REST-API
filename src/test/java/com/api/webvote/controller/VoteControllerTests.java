package com.api.webvote.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
		client.setId(1L);
		client.setVotedSchedules("10;");
		
		when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

		Schedule schedule = new Schedule("Test Schedule", 1L);
		schedule.setId(1L);
		schedule.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
		schedule.setEndTime(Timestamp.valueOf(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime().plusMinutes(5)));
		
		when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

		Vote vote = new Vote(1L, "Sim", 1L, 1L);
		
		when(voteRepository.save(vote)).thenReturn(vote);
		
		MvcResult result = mockMvc.perform(post("/api/vote/save").content(asJsonString(vote)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		
	}

	@Test
	public void deveRetornarFalha_clienteJaVotouNaPauta() throws Exception 
	{
		Client client = new Client("Test Client", "150.000.000-00");
		client.setId(1L);
		client.setVotedSchedules("1;");
		
		when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

		Schedule schedule = new Schedule("Test Schedule", 1L);
		schedule.setId(1L);
		schedule.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
		schedule.setEndTime(Timestamp.valueOf(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime().plusMinutes(5)));
		
		when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

		Vote vote = new Vote(1L, "Sim", 1L, 1L);
		
		when(voteRepository.save(vote)).thenReturn(vote);
		
		MvcResult result = mockMvc.perform(post("/api/vote/save").content(asJsonString(vote)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void deveRetornarFalha_tempoPautaExpirado() throws Exception {

		Client client = new Client("Test Client", "150.000.000-00");
		client.setId(1L);
		client.setVotedSchedules("10;");
		
		when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

		Schedule schedule = new Schedule("Test Schedule", 1L);
		schedule.setId(1L);
		schedule.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
		schedule.setEndTime(Timestamp.valueOf(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime().plusMinutes(-5)));
		
		when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

		Vote vote = new Vote(1L, "Sim", 1L, 1L);
		
		when(voteRepository.save(vote)).thenReturn(vote);
		
		MvcResult result = mockMvc.perform(post("/api/vote/save").content(asJsonString(vote)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void deveRetornarFalha_votoDiferenteSimNao() throws Exception {

		Client client = new Client("Test Client", "150.000.000-00");
		client.setId(1L);
		client.setVotedSchedules("10;");
		
		when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

		Schedule schedule = new Schedule("Test Schedule", 1L);
		schedule.setId(1L);
		schedule.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
		schedule.setEndTime(Timestamp.valueOf(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime().plusMinutes(-5)));
		
		when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

		Vote vote = new Vote(1L, "Simm", 1L, 1L);
		
		when(voteRepository.save(vote)).thenReturn(vote);
		
		MvcResult result = mockMvc.perform(post("/api/vote/save").content(asJsonString(vote)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
		
	}

	@Test
	public void deveRetornarFalha_pautaNaoEncontrada() throws Exception {

		Client client = new Client("Test Client", "150.000.000-00");
		ResponseEntity<Client> clientExpected = clientController.save(client);

		Schedule schedule = new Schedule("Test Schedule", 1L);
		ResponseEntity<Schedule> scheduleExpected = scheduleController.newSchedule(schedule);
		
		Vote vote = new Vote(1L, "Sim", clientExpected.getBody().getId() , scheduleExpected.getBody().getId());
		ResponseEntity<Vote> voteExpected = voteController.save(vote);
		
		assertEquals(HttpStatus.NOT_FOUND, voteExpected.getStatusCode());
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

}
