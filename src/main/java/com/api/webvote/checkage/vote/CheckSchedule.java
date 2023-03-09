package com.api.webvote.checkage.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.webvote.controller.VoteController;
import com.api.webvote.exception.NotFoundException;
import com.api.webvote.model.Schedule;

public class CheckSchedule implements VoteChecker {

	final Logger logger = LoggerFactory.getLogger(VoteController.class);
	private Schedule schedule;

	public CheckSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	@Override
	public void execute() {
		
		logger.debug("-> Buscando pauta no banco de dados.");

		if (schedule == null)
			throw new NotFoundException("A pauta não foi encontrada.");

	}
}
