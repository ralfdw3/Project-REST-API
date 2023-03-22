package com.api.webvote.tests.controller;

import static org.mockito.Mockito.when;
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

import com.api.webvote.v1.controller.VoteController;
import com.api.webvote.v1.enums.VotoEnum;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;
import com.api.webvote.v1.service.vote.VoteServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VoteController.class)
@ContextConfiguration(classes = VoteController.class)
public class VoteControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VoteServiceInterface voteService;

	private Vote voteMock;
	private Associate clientMock;
	private Schedule scheduleMock;

	@BeforeEach
	public void inicialize() {

		List<Vote> votes = new ArrayList<Vote>();
		clientMock = new Associate(1L, "Ralf Drehmer Wink", "000.000.000-00");

		scheduleMock = new Schedule(1L, "Schedule title", votes, 1, LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(1));

		voteMock = new Vote(1L, VotoEnum.SIM, clientMock, scheduleMock);

		when(voteService.save(voteMock)).thenReturn(ResponseEntity.ok().build());
	}

	@Test
	public void deveRetornarSucesso_salvaNovoCliente() throws Exception {

		mockMvc.perform(
				post("/v1/api/vote/new").contentType(MediaType.APPLICATION_JSON).content(asJsonString(voteMock)))
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
