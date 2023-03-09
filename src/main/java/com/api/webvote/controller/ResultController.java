package com.api.webvote.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.api.webvote.model.Schedule;
import com.api.webvote.repository.ScheduleRepository;

@RestController
public class ResultController {
	
	private static final Logger logger = LoggerFactory.getLogger(ResultController.class);
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	@GetMapping(path = "/api/schedule/result/{id}")
	public ResponseEntity<String> countVotes (@PathVariable("id") Long id) throws Exception{
		Optional<Schedule> schedule = scheduleRepository.findById( id );

		if( schedule == null ){
			return ResponseEntity.notFound().build();
		}
		
		int yesVotes = schedule.get().getYesVotesCount();
		int noVotes = schedule.get().getNoVotesCount();

		int calc = yesVotes + noVotes;
		
		logger.debug("-> Esta pauta teve um total de " + calc +	" votos, sendo destes " + yesVotes + " votos 'Sim' e " + noVotes + " votos 'Não'");
		
		String result = "Esta pauta teve um total de " + calc + 
				" votos, sendo destes " + yesVotes + " votos 'Sim' e " + noVotes + 
				" votos 'Não'";
		
		return ResponseEntity.ok(result);
	}
}
