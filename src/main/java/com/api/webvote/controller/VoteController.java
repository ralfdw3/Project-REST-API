package com.api.webvote.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.webvote.model.Client;
import com.api.webvote.model.Schedule;
import com.api.webvote.model.Vote;
import com.api.webvote.repository.ClientRepository;
import com.api.webvote.repository.ScheduleRepository;
import com.api.webvote.repository.VoteRepository;

@RestController
public class VoteController {

	@Autowired
	private VoteRepository voteRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private ClientRepository clientRepository;

	@PostMapping(path = "/api/vote/save")
	public ResponseEntity<Vote> save(@RequestBody Vote vote) {

		final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

		Long idClient = vote.getClientId();
		Long idSchedule = vote.getScheduleId();
		String value = vote.getVote();

		// Verifica se a resposta do usuário é 'Sim'/'Não'
		if (!value.equals("Sim") && !value.equals("Não")) {
			logger.debug("-> A resposta do cliente foi diferente de 'Sim'/'Não'.");
			return ResponseEntity.badRequest().build();
		}

		// Busca o objeto schedule
		Optional<Schedule> optionalSchedule = scheduleRepository.findById(idSchedule);
		if (optionalSchedule.isEmpty()) {
			logger.debug("-> Pauta não encontrada.");
			return ResponseEntity.notFound().build();
		}

		Schedule schedule = scheduleRepository.findById(idSchedule).get();

		// Verifica se o cliente já votou nesta pauta
		Client client = clientRepository.findById(idClient).get();
		String votedSchedules = client.getVotedSchedules();

		for (String s : votedSchedules.split(";")) {

			if (Long.valueOf(s) == idSchedule) {
				logger.debug("-> Este cliente já votou nesta pauta.");
				return ResponseEntity.badRequest().build();
			}
		}

		// Verifica se o tempo da pauta já expirou
		if (schedule.getEndTime().toLocalDateTime().isBefore(LocalDateTime.now()))

		{
			logger.debug("-> O tempo de votação da pauta já expirou");
			return ResponseEntity.badRequest().build();
		}

		// Contabilizar a quantidade de votos "Sim"e "Não"
		int yesVotes = schedule.getYesVotesCount();
		int noVotes = schedule.getNoVotesCount();

		if (value.equals("Sim")) {
			yesVotes++;
		} else {
			noVotes++;
		}

		schedule.setYesVotesCount(yesVotes);
		schedule.setNoVotesCount(noVotes);

		votedSchedules += idSchedule.toString() + ";";
		client.setVotedSchedules(votedSchedules);

		clientRepository.save(client);
		scheduleRepository.save(schedule);
		voteRepository.save(vote);
		
		logger.debug("-> Voto contabilizado");
		return ResponseEntity.ok(vote);

	}

}

