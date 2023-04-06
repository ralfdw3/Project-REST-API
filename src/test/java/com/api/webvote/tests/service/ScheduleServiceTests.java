package com.api.webvote.tests.service;

import com.api.webvote.tests.stubs.ScheduleStub;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.exception.NotFoundException;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.repository.ScheduleRepository;
import com.api.webvote.v1.service.schedule.check.CheckTitle;
import com.api.webvote.v1.service.schedule.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

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

	private Schedule scheduleDefault = ScheduleStub.scheduleDefault();

	@BeforeEach
	public void setup() {
		openMocks(this);
		when(scheduleRepository.save(scheduleDefault)).thenReturn(scheduleDefault);
		when(scheduleRepository.findById(1L)).thenReturn(Optional.of(scheduleDefault));
	}

	@Test
	public void Should_ReturnOk_When_CreateScheduleWithValidTitle () throws Exception {
		assertDoesNotThrow(() -> new CheckTitle().check(scheduleDefault));
	}
	@Test
	public void Should_ThrowBadRequestException_When_CreateScheduleWithNullTitle () throws Exception {
		scheduleDefault.setTitle(null);
		assertThrows(BadRequestException.class, () -> new CheckTitle().check(scheduleDefault));
	}

	@Test
	public void Should_ThrowBadRequestException_When_CreateScheduleWithNullEmpty() throws Exception {
		scheduleDefault.setTitle("");
		assertThrows(BadRequestException.class, () -> new CheckTitle().check(scheduleDefault));
	}
	
	@Test
	public void Should_ReturnOk_When_GetScheduleResultsById () throws Exception {
		assertEquals("Esta pauta teve um total de 0 votos 'Sim' e 0 votos 'Não'", scheduleService.results(1L).getBody());
	}
	
	@Test
	public void Should_ThrowNotFoundException_When_GetScheduleResultsWithInvalidId () throws Exception {
		when(scheduleRepository.findById(any())).thenThrow(NotFoundException.class);
		assertThrows(NotFoundException.class, () -> scheduleService.results(any()));
	}
}
