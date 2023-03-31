package com.api.webvote.v1.service.check;

import com.api.webvote.v1.controller.VoteController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CheckVotes {

	final static Logger logger = LoggerFactory.getLogger(VoteController.class);

	public static void check(Associate associate, Schedule schedule) {
		logger.debug("-> Verificando se este associado já votou nesta pauta.");

		if(schedule.getVotes().stream().anyMatch(vote -> vote.getAssociate().getId().equals(associate.getId()))){
			throw new BadRequestException("O associado já votou nesta pauta.");
		}
	}
}
