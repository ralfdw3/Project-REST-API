package com.api.webvote.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

		@Autowired
		private ResultController resultController;

		@MockBean
		private ClientRepository clientRepository;

		@MockBean
		private ScheduleRepository scheduleRepository;

		@MockBean
		private VoteRepository voteRepository;

		@BeforeEach
		public void setup() {
			standaloneSetup(this.resultController);
		}
		
		@Test
		public void deveRetornarVotos_QuandoBuscarPautaPorId() throws Exception {
			
		  // cria um registro de pauta de teste e salva no banco de dados mockado
		  Schedule testSchedule = new Schedule(1L, "Test Schedule", 2L, 10, null, null, 0, 0);
		  
		  when(scheduleRepository.findById( 1L )).thenReturn( Optional.of(testSchedule) );

		  // realiza uma chamada GET para a API passando o ID da pauta de teste
		  MvcResult result = mockMvc.perform(get("/api/schedule/result/{id}", testSchedule.getId()))
		    .andExpect(status().isOk())
		    .andReturn();

		  // verifica se o método findById() do repositório de pautas foi chamado com o ID da pauta

		  verify(scheduleRepository, times( 3 )).findById(testSchedule.getId());
		  // verifica se não houve mais interações com o repositório de pautas
		  verifyNoMoreInteractions(scheduleRepository);

		  // verifica se o corpo da resposta é a string esperada
		  String responseBody = result.getResponse().getContentAsString();
		  Assertions.assertEquals("Esta pauta teve um total de 0 votos, sendo destes 0 votos 'Sim' e 0 votos 'Não'", responseBody);
		}
		
		@Test
		public void deveRetornarFalha_pautaNaoEncontrada() throws Exception {

			mockMvc.perform(get("/api/schedule/result/{id}", 99999)).andExpect(status().isNotFound());
			
		}
}

