package com.api.webvote.tests.controller;

import com.api.webvote.tests.config.Convert;
import com.api.webvote.v1.controller.AssociateController;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.service.associate.AssociateServiceInterface;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
		associate = new Associate(1L, "Ralf", "000.000.000-00");
		when(clientService.get(1L)).thenReturn(ResponseEntity.ok(associate));
		when(clientService.get(99999L)).thenReturn(ResponseEntity.notFound().build());
	}

	@Test
	public void deveRetornarSucesso_aoBuscarAssociadoPelaId() throws Exception {
		mockMvc.perform(get("/v1/api/associate/{id}", 1L))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(associate.getId()))
		.andExpect(jsonPath("$.name").value("Ralf"))
		.andExpect(jsonPath("$.cpf").value(associate.getCpf()));
	}

	@Test
	public void deveRetornarFalha_aoBuscarAssociadoComIdInvalido() throws Exception {
		mockMvc.perform(get("/v1/api/associate/{id}", 99999L))
				.andExpect(status().isNotFound());
	}
	@Test
	public void deveRetornarSucesso_aoCriarNovoAssociado() throws Exception {
		mockMvc.perform(post("/v1/api/associate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Convert.asJsonString(associate)))
				.andExpect(status().isOk());
	}

	@Test
	public void deveRetornarFalha_aoCriarNovoAssociado() throws Exception {
		mockMvc.perform(post("/v1/api/associate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Convert.asJsonString(null)))
						.andExpect(status().isBadRequest());
	}

}
