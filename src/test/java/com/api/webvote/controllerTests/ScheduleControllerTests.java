package com.api.webvote.controllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.api.webvote.controller.ScheduleController;
import com.api.webvote.exception.BadRequestException;
import com.api.webvote.model.Schedule;
import com.api.webvote.repository.ClientRepository;
import com.api.webvote.repository.ScheduleRepository;
import com.api.webvote.repository.VoteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ClientRepository clientRepository;

	@MockBean
	private ScheduleRepository scheduleRepository;

	@MockBean
	private VoteRepository voteRepository;

	@Test
	public void deveRetornarSucesso_CriarNovoSchedule() throws Exception {
		Schedule scheduleMock = new Schedule(1L, "Schedule title", 1L, 1, LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(1), 0, 0);

		mockMvc.perform(
				post("/api/schedule/new").content(asJsonString(scheduleMock)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void deveRetornarFalha_CriarScheduleComTituloNull() throws Exception {
		Schedule scheduleMock = new Schedule(null, 1L);
		
		mockMvc.perform(
				post("/api/schedule/new").content(asJsonString(scheduleMock)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}
	
	@Test
	public void deveRetornarFalha_CriarScheduleComClientIdNull() throws Exception {
		Schedule scheduleMock = new Schedule("Titulo teste", null);
		
		mockMvc.perform(
				post("/api/schedule/new").content(asJsonString(scheduleMock)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}
	
	@Test
	public void deveRetornarFalha_CriarScheduleComTituloEmpty() throws Exception {
		Schedule scheduleMock = new Schedule("", 1L);
		
		mockMvc.perform(
				post("/api/schedule/new").content(asJsonString(scheduleMock)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	public static String asJsonString(final Object obj) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new BadRequestException(e.toString());
		}
	}

}
