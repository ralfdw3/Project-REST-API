package com.api.webvote.controllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.api.webvote.model.Schedule;
import com.api.webvote.repository.ClientRepository;
import com.api.webvote.repository.ScheduleRepository;
import com.api.webvote.repository.VoteRepository;

@WebMvcTest
public class ResultControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ClientRepository clientRepository;

	@MockBean
	private ScheduleRepository scheduleRepository;

	@MockBean
	private VoteRepository voteRepository;

	/*
	 * @BeforeEach public void setup() { standaloneSetup(this.resultController); }
	 */

	Schedule scheduleMock = new Schedule(1L, "Test Schedule", 2L, 10, LocalDateTime.now(),
			LocalDateTime.now().plusMinutes(1), 0, 0);

	@Test
		public void deveRetornarVotos_QuandoBuscarPautaPorId() throws Exception {
		  when(scheduleRepository.findById( 1L )).thenReturn( Optional.of(scheduleMock) );

		  MvcResult result = mockMvc.perform(get("/api/schedule/result/{id}", scheduleMock.getId()))
		    .andExpect(status().isOk())
		    .andReturn();

		  	verify(scheduleRepository, times( 1 )).findById(scheduleMock.getId());
		  
		  String responseBody = result.getResponse().getContentAsString();
		  assertEquals("Esta pauta teve um total de 0 votos, sendo destes 0 votos 'Sim' e 0 votos 'Não'", responseBody);
		}

	@Test
	public void deveRetornarFalha_pautaNaoEncontrada() throws Exception {
		when(scheduleRepository.findById( 99999L )).thenReturn(null);

		mockMvc.perform(get("/api/schedule/result/{id}", 99999L)).andExpect(status().isNotFound());

	}
}
