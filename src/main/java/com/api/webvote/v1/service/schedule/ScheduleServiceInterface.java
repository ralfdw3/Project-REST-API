package com.api.webvote.v1.service.schedule;

import org.springframework.http.ResponseEntity;

import com.api.webvote.v1.model.Schedule;

public interface ScheduleServiceInterface {
	
	public ResponseEntity<Schedule> save(Schedule schedule);
	public ResponseEntity<String> results (Long id);

}
