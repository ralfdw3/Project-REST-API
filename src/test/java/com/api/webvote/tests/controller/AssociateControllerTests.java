package com.api.webvote.tests.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.api.webvote.v1.controller.AssociateController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.service.associate.AssociateServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AssociateController.class)
@ContextConfiguration(classes = AssociateController.class)
public class AssociateControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AssociateServiceInterface clientService;
	
	private Associate associate;

	@BeforeEach
	public void inicialize() {
		associate = new Associate(1L, "Ralf", "035.592.500-15");
		when(clientService.get(1L)).thenReturn(ResponseEntity.ok(associate));
		when(clientService.get(2L)).thenReturn(ResponseEntity.badRequest().build());
	}

	@Test
	public void deveRetornarSucesso_buscaClientePelaId() throws Exception {
		mockMvc.perform(get("/v1/api/client/{id}", 1L))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(associate.getId()))
		.andExpect(jsonPath("$.name").value("Ralf"))
		.andExpect(jsonPath("$.cpf").value(associate.getCpf()));
	}

	@Test
	public void deveRetornarSucesso_salvaNovoCliente() throws Exception {
		mockMvc.perform(post("/v1/api/client/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(associate)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deveRetornarFalha_buscaClienteComIdInvalido() throws Exception {
		mockMvc.perform(get("/v1/api/client/{id}", 2L))
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
