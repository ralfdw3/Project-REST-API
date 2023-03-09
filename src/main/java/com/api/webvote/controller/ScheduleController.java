package com.api.webvote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.webvote.model.Schedule;
import com.api.webvote.repository.ScheduleRepository;

@RestController
public class ScheduleController {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@PostMapping(path = "/api/schedule/new")
	public ResponseEntity<Schedule> newSchedule(@RequestBody Schedule schedule) {
		String title = schedule.getTitle();
		Long clientId = schedule.getClientId();
		
		if (clientId == null || title == null || title.isEmpty())
			return ResponseEntity.badRequest().build();
		
	    logger.debug("-> Criando nova pauta: " + schedule.getTitle());
	    scheduleRepository.save(schedule);
	    return ResponseEntity.ok(schedule);
	}

}
