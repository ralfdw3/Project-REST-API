package com.api.webvote.tests.controller;

import com.api.webvote.tests.config.Convert;
import com.api.webvote.v1.controller.ScheduleController;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.service.schedule.ScheduleServiceInterface;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ScheduleController.class)
@ContextConfiguration(classes = ScheduleController.class)
public class ScheduleControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ScheduleServiceInterface scheduleService;
	private Schedule scheduleMock;

	@BeforeEach
	public void inicialize() {
		scheduleMock = new Schedule();
		scheduleMock.setTitle("Titulo da pauta");
		
		when(scheduleService.results(1L)).thenReturn(ResponseEntity.ok().build());
		when(scheduleService.results(99999L)).thenReturn(ResponseEntity.notFound().build());
	}

	@Test
	public void deveRetornarSucesso_aoCriarNovaPauta() throws Exception{
		mockMvc.perform(post("/v1/api/schedule")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Convert.asJsonString(scheduleMock)))
				.andExpect(status().isOk());
	}

	@Test
	public void deveRetornarFalha_aoCriarNovaPauta() throws Exception{
		mockMvc.perform(post("/v1/api/schedule")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Convert.asJsonString(null)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deveRetornarSucesso_aoBuscarResultadosDaPautaPeloId() throws Exception {
		mockMvc.perform(get("/v1/api/schedule/{id}", 1L))
		.andExpect(status().isOk());
	}

	@Test
	public void deveRetornarFalha_aoBuscarResultadosDaPautaPeloIdInvalido() throws Exception {
		mockMvc.perform(get("/v1/api/schedule/{id}", 99999L))
				.andExpect(status().isNotFound());
	}

}
