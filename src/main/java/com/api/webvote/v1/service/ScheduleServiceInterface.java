package com.api.webvote.v1.service;

import org.springframework.http.ResponseEntity;

import com.api.webvote.v1.model.Schedule;

public interface ScheduleServiceInterface {
	
	public ResponseEntity<Schedule> save(Schedule schedule);
	public ResponseEntity<String> getResults (Long id);

}
