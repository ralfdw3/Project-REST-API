package com.api.webvote.v1.controller;

import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.service.schedule.ScheduleServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/api/schedule")
@RestController
public class ScheduleController {

	private final ScheduleServiceInterface scheduleServiceInterface;

	@Autowired
	public ScheduleController(ScheduleServiceInterface scheduleServiceInterface) {
		this.scheduleServiceInterface = scheduleServiceInterface;
	}

	@PostMapping
	public ResponseEntity<Schedule> newSchedule(@Valid @RequestBody Schedule schedule) {
		return scheduleServiceInterface.save(schedule);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<String> results (@PathVariable("id") Long id) throws Exception{
		return scheduleServiceInterface.results(id);
	}

}
