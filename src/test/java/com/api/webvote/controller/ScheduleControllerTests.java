package com.api.webvote.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.api.webvote.model.Schedule;
import com.api.webvote.repository.ClientRepository;
import com.api.webvote.repository.ScheduleRepository;
import com.api.webvote.repository.VoteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class ScheduleControllerTests {
	
	@Autowired
	private MockMvc mockMvc;

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
		standaloneSetup(this.scheduleController);
	}
	
	@Test
	public void deveCriarNovoSchedule() throws Exception {
		
		Long id = 15L;
		String title = "title";
		Long clientId = 3L;
		int durationTime = 10;
		Timestamp startTime = Timestamp.valueOf(LocalDateTime.now());
		Timestamp endTime = Timestamp.valueOf(startTime.toLocalDateTime().plusMinutes(5));
		int yesVotesCount = 3;
		int noVotesCount = 2;
		
	    Schedule schedule = new Schedule(id, title, clientId, durationTime, startTime, endTime,
				yesVotesCount, noVotesCount);
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    String json = objectMapper.writeValueAsString(schedule);

	    mockMvc.perform(post("/api/schedule/new")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(json))
	            .andExpect(status().isOk());
	}

}
