package com.api.webvote.v1.service.schedule;

import com.api.webvote.v1.enums.VotoEnum;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.repository.ScheduleRepository;
import com.api.webvote.v1.service.check.CheckTitle;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService implements ScheduleServiceInterface {

	private ScheduleRepository scheduleRepository;

	@Autowired
	public ScheduleService(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	@Transactional
	@Override
	public ResponseEntity<Schedule> save (Schedule schedule) {
		CheckTitle.check(schedule.getTitle());

		scheduleRepository.save(schedule);
		return ResponseEntity.ok(schedule);

	}

	@Override
	public ResponseEntity<String> results(Long id) {
		
		ResponseEntity<Schedule> schedule = scheduleRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
		
		long yesVotes = schedule.getBody().getVotes().stream().filter(voto -> voto.getVote().equals(VotoEnum.SIM)).count();
		long noVotes = schedule.getBody().getVotes().stream().filter(voto -> voto.getVote().equals(VotoEnum.NAO)).count();
		
		String result = "Esta pauta teve um total de " + yesVotes + " votos 'Sim' e " + noVotes + " votos 'Não'";
		
		return ResponseEntity.ok(result);
	}

}
