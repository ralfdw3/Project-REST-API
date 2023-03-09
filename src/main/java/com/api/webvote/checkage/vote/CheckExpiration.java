package com.api.webvote.checkage.vote;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.webvote.controller.VoteController;
import com.api.webvote.exception.BadRequestException;
import com.api.webvote.model.Schedule;

public class CheckExpiration implements VoteChecker{
	
	private Schedule schedule;
	
	public CheckExpiration(Schedule schedule) {
		this.schedule = schedule;
	}

	@Override
	public void execute() {
		final Logger logger = LoggerFactory.getLogger(VoteController.class);
		logger.debug("-> Verificando se o tempo de votação da pauta já expirou");
		
		if (schedule.getEndTime().isBefore(LocalDateTime.now()))
			throw new BadRequestException("Período de votação na pauta encerrado.");
		
	}

}