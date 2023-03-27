package com.api.webvote.tests.service;

import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.exception.NotFoundException;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;
import com.api.webvote.v1.repository.ScheduleRepository;
import com.api.webvote.v1.service.check.CheckTitle;
import com.api.webvote.v1.service.schedule.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ScheduleServiceTests {

	@InjectMocks
	private ScheduleService scheduleService;

	@Mock
	private ScheduleRepository scheduleRepository;

	@BeforeEach
	public void conditions() {
		openMocks(this);
		when(scheduleRepository.save(scheduleMock)).thenReturn(scheduleMock);
		when(scheduleRepository.findById(1L)).thenReturn(Optional.of(scheduleMock));
		when(scheduleRepository.findById(2L)).thenThrow(NotFoundException.class);
	}

	// Variáveis mocks
	List<Vote> votes = new ArrayList<Vote>();

	Schedule scheduleMock = new Schedule(1L, "Schedule title", votes, 1, LocalDateTime.now(),
			LocalDateTime.now().plusMinutes(1));

	@Test
	public void deveRetornarSucesso_aoCriarNovoSchedule() throws Exception {
		assertEquals(HttpStatus.OK, scheduleService.save(scheduleMock).getStatusCode());
	}

	@Test
	public void deveRetornarSucesso_aoCriarScheduleComTituloValido() throws Exception {
		assertDoesNotThrow(() -> CheckTitle.check(scheduleMock.getTitle()));
	}
	@Test
	public void deveRetornarFalha_aoCriarScheduleComTituloNull() throws Exception {
		scheduleMock.setTitle(null);
		assertThrows(BadRequestException.class, () -> CheckTitle.check(scheduleMock.getTitle()));
	}

	@Test
	public void deveRetornarFalha_aoCriarScheduleComTituloEmpty() throws Exception {
		scheduleMock.setTitle("");
		assertThrows(BadRequestException.class, () -> CheckTitle.check(scheduleMock.getTitle()));
	}
	
	@Test
	public void deveRetornarSucesso_aoBuscarOResultadoDeUmaPauta() throws Exception {
		assertEquals("Esta pauta teve um total de 0 votos 'Sim' e 0 votos 'Não'", scheduleService.results(1L).getBody());
	}
	
	@Test
	public void deveRetornarFalha_aoBuscarOResultadoDeUmaPautaComIdInvalido() throws Exception {
		assertThrows(NotFoundException.class, () -> scheduleService.results(2L));
	}

}
