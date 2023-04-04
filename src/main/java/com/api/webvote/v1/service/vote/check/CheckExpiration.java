package com.api.webvote.v1.service.vote.check;

import com.api.webvote.v1.controller.VoteController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CheckExpiration implements VoteSystemChecker {
	
	final static Logger logger = LoggerFactory.getLogger(VoteController.class);

	@Override
	public void check(Vote vote) {
		logger.debug("-> Verificando se o tempo de votação da pauta já expirou");

		if (vote.getSchedule().getEnd().isBefore(LocalDateTime.now()))
			throw new BadRequestException("Período de votação na pauta encerrado.");

	}
}