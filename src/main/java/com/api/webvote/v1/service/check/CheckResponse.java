package com.api.webvote.v1.service.check;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.webvote.v1.controller.VoteController;
import com.api.webvote.v1.enums.VotoEnum;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Vote;

public class CheckResponse {
	
	final static Logger logger = LoggerFactory.getLogger(VoteController.class);

	public static void check(Vote vote) {
		logger.debug("-> Verificando se a resposta do cliente foi 'SIM'/'NAO'.");
		
		VotoEnum value = vote.getVote();
		
		if (value != VotoEnum.SIM && value != VotoEnum.NAO) 
			throw new BadRequestException("Resposta inválida.");
	}

}
