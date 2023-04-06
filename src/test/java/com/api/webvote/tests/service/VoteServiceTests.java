package com.api.webvote.tests.service;

import com.api.webvote.tests.stubs.AssociateStub;
import com.api.webvote.tests.stubs.ScheduleStub;
import com.api.webvote.tests.stubs.VoteStub;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;
import com.api.webvote.v1.repository.VoteRepository;
import com.api.webvote.v1.service.vote.check.CheckExpiration;
import com.api.webvote.v1.service.vote.check.CheckResponse;
import com.api.webvote.v1.service.vote.check.CheckVotes;
import com.api.webvote.v1.service.vote.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class VoteServiceTests {

	@InjectMocks
	private VoteService voteService;

	@Mock
	private VoteRepository voteRepository;

	private Vote voteDefault = VoteStub.voteDefault();

	@BeforeEach
	public void setup() {
		openMocks(this);
		when(voteRepository.save(voteDefault)).thenReturn(voteDefault);
	}
	@Test
	public void Should_ReturnOk_When_CheckingAssociateAnswer () throws Exception {
		assertDoesNotThrow(() -> new CheckResponse().check(voteDefault));
	}
	@Test
	public void Should_ThrowBadRequestException_When_AssociateVoteIsNull () throws Exception {
		assertThrows(BadRequestException.class, () -> new CheckResponse().check(VoteStub.voteWithVoteNull()));
	}
	@Test
	public void Should_ReturnOk_When_CheckingIfAssociateAlreadyVotedInTheSchedule () throws Exception {
		assertDoesNotThrow(() -> new CheckVotes().check(voteDefault));
	}
	@Test
	public void Should_ThrowBadRequestException_When_CheckingIfAssociateAlreadyVotedInTheSchedule () throws Exception {
		assertThrows(BadRequestException.class, () -> new CheckVotes().check(VoteStub.voteWhenAssociateAlreadyVoted()));
	}
	@Test
	public void Should_ThrowBadRequestException_When_CheckingIfScheduleIsExpired () throws Exception {
		assertThrows(BadRequestException.class, () -> new CheckExpiration().check(VoteStub.voteWithScheduleExpired()));
	}
	@Test
	public void Should_ReturnOk_When_CheckingIfScheduleIsExpired () throws Exception {
		assertDoesNotThrow(() -> new CheckExpiration().check(voteDefault));
	}
}
