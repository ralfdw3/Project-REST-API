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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import com.api.webvote.v1.enums.VotoEnum;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;
import com.api.webvote.v1.repository.VoteRepository;
import com.api.webvote.v1.service.check.CheckExpiration;
import com.api.webvote.v1.service.vote.VoteService;

public class VoteServiceTests {

	@InjectMocks
	private VoteService voteService;

	@Mock
	private VoteRepository voteRepository;

	@BeforeEach
	public void conditions() {
		openMocks(this);
		when(voteRepository.save(voteMock)).thenReturn(voteMock);
	}

	// Variáveis mocks
	List<Vote> votes = new ArrayList<Vote>();
	Associate clientMock = new Associate(1L, "Ralf Drehmer Wink", "000.000.000-00");

	Schedule scheduleMock = new Schedule(1L, "Schedule title", votes, 1, LocalDateTime.now(),
			LocalDateTime.now().plusMinutes(1));

	Vote voteMock = new Vote(1L, VotoEnum.SIM, clientMock, scheduleMock);

	@Test
	public void deveRetornarSucesso_novoVoto() throws Exception {
		ResponseEntity<Vote> response = voteService.save(voteMock);

		verify(voteRepository, times(1)).save(any(Vote.class));
		assertEquals(response, ResponseEntity.ok().build());

	}

	@Test
	public void deveRetornarFalha_clienteJaVotouNaPauta() throws Exception {
		Vote newVote = new Vote(1L, VotoEnum.SIM, clientMock, scheduleMock);
		votes.add(newVote);

		ResponseEntity<Vote> response = voteService.save(voteMock);

		verify(voteRepository, times(0)).save(any(Vote.class));
		assertEquals(ResponseEntity.badRequest().build(), response);
		//assertThrows(BadRequestException.class, () -> CheckVotes.check(clientMock, scheduleMock));

	}

	@Test
	public void deveRetornarFalha_tempoPautaExpirado() throws Exception {
		scheduleMock.setEnd(LocalDateTime.now().plusMinutes(-5));

		ResponseEntity<Vote> response = voteService.save(voteMock);

		verify(voteRepository, times(0)).save(any(Vote.class));
		assertEquals(ResponseEntity.badRequest().build(), response);
		assertThrows(BadRequestException.class, () -> CheckExpiration.check(scheduleMock));

	}

}
