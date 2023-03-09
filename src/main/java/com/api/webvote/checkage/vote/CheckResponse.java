package com.api.webvote.checkage.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.webvote.controller.VoteController;
import com.api.webvote.exception.BadRequestException;
import com.api.webvote.model.Vote;

public class CheckResponse implements VoteChecker{
	
	private Vote vote;
	
	public CheckResponse(Vote vote) {
		this.vote = vote;
	}

	@Override
	public void execute() {
		final Logger logger = LoggerFactory.getLogger(VoteController.class);
		logger.debug("-> Verificando se a resposta do cliente foi 'Sim'/'Não'.");
		
		String value = vote.getVote();
		
		if (!value.equals("Sim") && !value.equals("Não")) 
			throw new BadRequestException("Resposta inválida.");
	}

}
