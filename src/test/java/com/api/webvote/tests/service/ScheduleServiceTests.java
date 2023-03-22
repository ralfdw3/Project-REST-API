package com.api.webvote.tests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import com.api.webvote.v1.exception.NotFoundException;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;
import com.api.webvote.v1.repository.ScheduleRepository;
import com.api.webvote.v1.service.schedule.ScheduleService;

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
	public void deveRetornarSucesso_CriarNovoSchedule() throws Exception {
		ResponseEntity<Schedule> response = scheduleService.save(scheduleMock);

		verify(scheduleRepository, times(1)).save(any(Schedule.class));
		assertEquals(response, ResponseEntity.ok().build());

	}

	@Test
	public void deveRetornarFalha_CriarScheduleComTituloNull() throws Exception {
		Schedule scheduleMock = new Schedule(1L, null, votes, 1, LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(1));

		ResponseEntity<Schedule> response = scheduleService.save(scheduleMock);

		verify(scheduleRepository, times(0)).save(any(Schedule.class));
		assertEquals(response, ResponseEntity.badRequest().build());
	}

	@Test
	public void deveRetornarFalha_CriarScheduleComTituloEmpty() throws Exception {
		Schedule scheduleMock = new Schedule(1L, "", votes, 1, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));

		ResponseEntity<Schedule> response = scheduleService.save(scheduleMock);

		verify(scheduleRepository, times(0)).save(any(Schedule.class));
		assertEquals(response, ResponseEntity.badRequest().build());
	}
	
	@Test
	public void deveRetornarSucesso_QuandoBuscarOResultadoDeUmaPauta() throws Exception {

		ResponseEntity<String> response = scheduleService.results(1L);
		String result = "Esta pauta teve um total de 0 votos 'Sim' e 0 votos 'Não'";
		assertEquals(response, ResponseEntity.ok(result));
	}
	
	@Test
	public void deveRetornarFalha_QuandoBuscarOResultadoDeUmaPauta() throws Exception {

		assertThrows(NotFoundException.class, () -> scheduleService.results(2L));
	}

}
