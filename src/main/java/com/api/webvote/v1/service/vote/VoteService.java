package com.api.webvote.v1.service.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Client;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;
import com.api.webvote.v1.repository.VoteRepository;
import com.api.webvote.v1.service.VoteServiceInterface;

import jakarta.transaction.Transactional;

@Service
public class VoteService implements VoteServiceInterface {

	private final VoteRepository voteRepository;

	@Autowired
	public VoteService(VoteRepository voteRepository) {
		this.voteRepository = voteRepository;
	}

	@Transactional
	@Override
	public ResponseEntity<Vote> save(Vote vote) {
		Schedule schedule = vote.getSchedule();
		Client client = vote.getClient();

		try {
			CheckExpiration.check(schedule);
			CheckResponse.check(vote);
			CheckVotes.check(client, schedule);
			
		} catch (BadRequestException e) {
			return ResponseEntity.badRequest().build();

		} 
		
		schedule.getVotes().add(vote);
		voteRepository.save(vote);
		return ResponseEntity.ok().build();

	}
}
