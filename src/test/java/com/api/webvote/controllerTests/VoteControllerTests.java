package com.api.webvote.controllerTests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

	@MockBean
	private ClientRepository clientRepository;

	@MockBean
	private ScheduleRepository scheduleRepository;

	@MockBean
	private VoteRepository voteRepository;

	@BeforeEach
	public void conditions() {
		when(clientRepository.findById(1L)).thenReturn(Optional.of(clientMock));
		when(scheduleRepository.findById(1L)).thenReturn(Optional.of(scheduleMock));
		when(voteRepository.save(voteMock)).thenReturn(voteMock);
	}

	Client clientMock = new Client(1L, "Client name", "150.000.000-00");

	Schedule scheduleMock = new Schedule(1L, "Schedule title", 1L, 1, LocalDateTime.now(),
			LocalDateTime.now().plusMinutes(1), 0, 0);

	Vote voteMock = new Vote(1L, "Sim", 1L, 1L);

	@Test
	public void deveRetornarSucesso_novoVoto() throws Exception {
		mockMvc.perform(post("/api/vote/save").content(asJsonString(voteMock)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
	}

	@Test
	public void deveRetornarFalha_clienteJaVotouNaPauta() throws Exception {
		clientMock.setVotedSchedules("1;");

		mockMvc.perform(post("/api/vote/save").content(asJsonString(voteMock)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	public void deveRetornarFalha_tempoPautaExpirado() throws Exception {
		scheduleMock.setEndTime(LocalDateTime.now().plusMinutes(-5));

		mockMvc.perform(post("/api/vote/save").content(asJsonString(voteMock)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	public void deveRetornarFalha_votoDiferenteSimNao() throws Exception {
		voteMock.setVote("YESSS");

		mockMvc.perform(post("/api/vote/save").content(asJsonString(voteMock)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	public void deveRetornarFalha_pautaNaoEncontrada() throws Exception {
		when(scheduleRepository.findById(1L)).thenReturn(null);
		
		mockMvc.perform(post("/api/vote/save").content(asJsonString(voteMock)).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest()).andReturn();
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
