package com.api.webvote.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.webvote.checkage.vote.AddVotedSchedules;
import com.api.webvote.checkage.vote.CheckExpiration;
import com.api.webvote.checkage.vote.CheckResponse;
import com.api.webvote.checkage.vote.CheckSchedule;
import com.api.webvote.checkage.vote.CheckVotes;
import com.api.webvote.checkage.vote.CountVotes;
import com.api.webvote.checkage.vote.VoteChecker;
import com.api.webvote.exception.BadRequestException;
import com.api.webvote.exception.NotFoundException;
import com.api.webvote.model.Client;
import com.api.webvote.model.Schedule;
import com.api.webvote.model.Vote;
import com.api.webvote.repository.ClientRepository;
import com.api.webvote.repository.ScheduleRepository;
import com.api.webvote.repository.VoteRepository;

import jakarta.transaction.Transactional;

@RestController
public class VoteController {

	@Autowired
	private VoteRepository voteRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private ClientRepository clientRepository;

	@PostMapping(path = "/api/vote/save")
	@Transactional
	public ResponseEntity<Vote> save(@RequestBody Vote vote) {
		final Logger logger = LoggerFactory.getLogger(VoteController.class);

		Long idClient = vote.getClientId();
		Long idSchedule = vote.getScheduleId();
		
		Optional<Schedule> schedule = scheduleRepository.findById(idSchedule);
		Optional<Client> client = clientRepository.findById(idClient);

		if( schedule == null || client == null){
			return ResponseEntity.badRequest().build();
		}
		
		try {
			List<VoteChecker> checkAndAction = Arrays.asList(
					new CheckExpiration(schedule.get()), 
					new CheckResponse(vote),
					new CheckSchedule(schedule.get()), 
					new CheckVotes(client.get(), vote), 
					new CountVotes(vote, schedule.get()), 
					new AddVotedSchedules(client.get(), schedule.get()));
			
			checkAndAction.forEach(obj -> obj.execute());
			
		} catch (BadRequestException e) {
			logger.debug("[ERRO] -> " + e.getMessage());
			return ResponseEntity.badRequest().build();
			
		} catch (NotFoundException e) {
			logger.debug("[ERRO] -> " + e.getMessage());
			return ResponseEntity.notFound().build();
		}

		clientRepository.save(client.get());
		scheduleRepository.save(schedule.get());
		voteRepository.save(vote);

		logger.debug("-> Voto contabilizado");
		return ResponseEntity.ok(vote);

	}

}
