package com.api.webvote.tests.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.api.webvote.v1.controller.ScheduleController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;
import com.api.webvote.v1.service.schedule.ScheduleServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ScheduleController.class)
@ContextConfiguration(classes = ScheduleController.class)
public class ScheduleControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ScheduleServiceInterface scheduleService;

	private Schedule scheduleMock;
	private String result;

	@BeforeEach
	public void inicialize() {
		List<Vote> votes = new ArrayList<Vote>();
		scheduleMock = new Schedule(
				1L, 
				"Pauta 1", 
				votes,
				10, 
				LocalDateTime.now(), 
				LocalDateTime.now().plusMinutes(10));
		
		result = "Esta pauta teve um total de 0 votos 'Sim' e 0 votos 'Não'";
		when(scheduleService.results(1L)).thenReturn(ResponseEntity.ok(result));
		when(scheduleService.results(2L)).thenReturn(ResponseEntity.badRequest().build());
	}

	@Test
	public void deveRetornarSucesso_CriarNovoSchedule() throws Exception {
		mockMvc.perform(
				post("/v1/api/schedule/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(scheduleMock)))
				.andExpect(status().isOk());

	}
	
	@Test
	public void deveRetornarSucesso_buscaPautaPelaId() throws Exception {
		mockMvc.perform(get("/v1/api/schedule/{id}", 1L))
		.andExpect(status().isOk());
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
