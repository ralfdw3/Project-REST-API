package com.api.webvote.v1.service.vote;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.webvote.v1.controller.VoteController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Schedule;

public class CheckExpiration {
	
	final static Logger logger = LoggerFactory.getLogger(VoteController.class);

	public static void check (Schedule schedule) {
		logger.debug("-> Verificando se o tempo de votação da pauta já expirou");
		
		if (schedule.getEnd().isBefore(LocalDateTime.now()))
			throw new BadRequestException("Período de votação na pauta encerrado.");
		
	}

}