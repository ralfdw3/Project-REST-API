package com.api.webvote.tests.controller_using_h2;

import com.api.webvote.tests.config.Convert;
import com.api.webvote.tests.stubs.ScheduleStub;
import com.api.webvote.v1.ApiWebvoteApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static com.api.webvote.tests.SqlProvider.insertSchedule;
import static com.api.webvote.tests.SqlProvider.resetDB;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ApiWebvoteApplication.class)
@AutoConfigureMockMvc
public class ScheduleControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = resetDB)
	public void Should_ReturnOk_When_CreateNewSchedule () throws Exception{
		mockMvc.perform(post("/v1/api/schedule")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Convert.asJsonString(ScheduleStub.scheduleDefault())))
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
	@SqlGroup({
			@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = insertSchedule),
			@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = resetDB)
	})
	public void Should_ReturnOk_When_GetScheduleResultsById () throws Exception {
		mockMvc.perform(get("/v1/api/schedule/{id}", 1L))
		.andExpect(status().isOk())
		.andExpect(content().string("Esta pauta teve um total de 0 votos 'Sim' e 0 votos 'Não'"));;
	}

	@Test
	public void Should_ReturnNotFound_When_GetScheduleResultsWithInvalidId () throws Exception {
		mockMvc.perform(get("/v1/api/schedule/{id}", 99999L))
				.andExpect(status().isNotFound());
	}

}
