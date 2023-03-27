package com.api.webvote.v1.service.schedule;

import com.api.webvote.v1.model.Schedule;
import org.springframework.http.ResponseEntity;

public interface ScheduleServiceInterface {
	
	public ResponseEntity<Schedule> save(Schedule schedule);
	public ResponseEntity<String> results (Long id);

}
