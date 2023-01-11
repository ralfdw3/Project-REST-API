package com.api.webvote.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.api.webvote.model.Schedule;
import com.api.webvote.repository.ScheduleRepository;

@RestController
public class ResultController {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	@GetMapping(path = "/api/schedule/result/{id}")
	public ResponseEntity<String> save (@PathVariable("id") Long id) throws Exception
	{
		Optional<Schedule> schedule = scheduleRepository.findById( id );

		if( schedule.isEmpty() )
		{
			return new ResponseEntity<String>("Schedule not found", HttpStatus.NOT_FOUND);
		}
		
		ResponseEntity<Integer> yesVotesResponse = scheduleRepository.findById(id)
				.map(record -> ResponseEntity.ok().body(record.getYesVotesCount()))
						.orElse(ResponseEntity.notFound().build());
		
		ResponseEntity<Integer> noVotesResponse = scheduleRepository.findById(id)
				.map(record -> ResponseEntity.ok().body(record.getNoVotesCount()))
						.orElse(ResponseEntity.notFound().build());
		
		int yesVotes = 0;
		int noVotes = 0;

		if( yesVotesResponse.getBody() != null )
			yesVotes = yesVotesResponse.getBody();

		if( noVotesResponse.getBody() != null )
			noVotes = noVotesResponse.getBody();			

		int calc = yesVotes + noVotes;
		
		logger.debug("-> Esta pauta teve um total de " + calc +	" votos, sendo destes " + yesVotes + " votos 'Sim' e " + noVotes + " votos 'Não'");
		
		String result = "Esta pauta teve um total de " + calc + 
				" votos, sendo destes " + yesVotes + " votos 'Sim' e " + noVotes + 
				" votos 'Não'";
		
		return ResponseEntity.ok(result);
	}
}
