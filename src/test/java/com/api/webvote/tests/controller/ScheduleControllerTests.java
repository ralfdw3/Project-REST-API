package com.api.webvote.tests.controller;

import com.api.webvote.tests.config.Convert;
import com.api.webvote.tests.stubs.ScheduleStub;
import com.api.webvote.v1.controller.ScheduleController;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.repository.ScheduleRepository;
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
	private Schedule scheduleDefault = ScheduleStub.scheduleDefault();

	@Test
	public void Should_ReturnOk_When_CreateNewSchedule () throws Exception{
		mockMvc.perform(post("/v1/api/schedule")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Convert.asJsonString(scheduleDefault)))
				.andExpect(status().isOk());
	}

	@Test
	public void Should_ReturnBadRequest_When_CreateNewSchedule () throws Exception{
		mockMvc.perform(post("/v1/api/schedule")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Convert.asJsonString(null)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void Should_ReturnOk_When_GetScheduleResultsById () throws Exception {
		mockMvc.perform(get("/v1/api/schedule/{id}", 1L))
		.andExpect(status().isOk());
	}

	@Test
	public void Should_ReturnNotFound_When_GetScheduleResultsWithInvalidId () throws Exception {
		when(scheduleService.results(99999L)).thenReturn(ResponseEntity.notFound().build());
		mockMvc.perform(get("/v1/api/schedule/{id}", 99999L))
				.andExpect(status().isNotFound());
	}

}
