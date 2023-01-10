package com.api.webvote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.api.webvote.repository.ScheduleRepository;

@RestController
public class ResultController {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	@GetMapping(path = "/api/schedule/result/{id}")
	public ResponseEntity<String> ScheduleById(@PathVariable("id") Long id) throws Exception
	{
		if (id == null) {
		    throw new Exception("Result not found");
		}
		
		ResponseEntity<Integer> yesVotes = scheduleRepository.findById(id)
				.map(record -> ResponseEntity.ok().body(record.getYesVotesCount()))
						.orElse(ResponseEntity.notFound().build());
		
		ResponseEntity<Integer> noVotes = scheduleRepository.findById(id)
				.map(record -> ResponseEntity.ok().body(record.getNoVotesCount()))
						.orElse(ResponseEntity.notFound().build());
		
		int calc = yesVotes.getBody()+noVotes.getBody();
		
		logger.debug("-> Esta pauta teve um total de " + calc +	" votos, sendo destes " + yesVotes + " votos 'Sim' e " + noVotes + " votos 'Não'");
		
		String result = "Esta pauta teve um total de " + calc + 
				" votos, sendo destes " + yesVotes.getBody() + " votos 'Sim' e " + noVotes.getBody() + 
				" votos 'Não'";
		
		return ResponseEntity.ok(result);
	}
}
