package com.api.webvote.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.service.ScheduleServiceInterface;

import jakarta.validation.Valid;

@RequestMapping("v1/api/schedule")
@RestController
public class ScheduleController {

	@Autowired
	private ScheduleServiceInterface scheduleServiceInterface;

	@PostMapping(path = "/new")
	public ResponseEntity<Schedule> newSchedule(@Valid @RequestBody Schedule schedule) {
		return scheduleServiceInterface.save(schedule);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<String> getResults (@PathVariable("id") Long id) throws Exception{
		return scheduleServiceInterface.getResults(id);
	}

}
