package com.api.webvote.v1.service.vote;

import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;
import com.api.webvote.v1.repository.VoteRepository;
import com.api.webvote.v1.service.check.CheckExpiration;
import com.api.webvote.v1.service.check.CheckResponse;
import com.api.webvote.v1.service.check.CheckVotes;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
		Associate client = vote.getAssociate();

		CheckExpiration.check(schedule);
		CheckResponse.check(vote);
		CheckVotes.check(client, schedule);

		schedule.getVotes().add(vote);
		voteRepository.save(vote);
		return ResponseEntity.ok().build();

	}
}
