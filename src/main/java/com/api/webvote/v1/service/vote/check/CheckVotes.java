package com.api.webvote.v1.service.vote.check;

import com.api.webvote.v1.controller.VoteController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CheckVotes implements VoteSystemChecker {

	final static Logger logger = LoggerFactory.getLogger(VoteController.class);

	@Override
	public void check(Vote vote) {
		logger.debug("-> Verificando se este associado já votou nesta pauta.");

		if(vote.getSchedule().getVotes().stream().anyMatch(v -> v.getAssociate().getId().equals(vote.getAssociate().getId()))){
			throw new BadRequestException("O associado já votou nesta pauta.");
		}

	}
}
