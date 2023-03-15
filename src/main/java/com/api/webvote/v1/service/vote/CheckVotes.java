package com.api.webvote.v1.service.vote;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.webvote.v1.controller.VoteController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Client;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;

public class CheckVotes {

	final static Logger logger = LoggerFactory.getLogger(VoteController.class);

	public static void check(Client client, Schedule schedule) {
		logger.debug("-> Verificando se este cliente já votou nesta pauta.");
		
		Long clientId = client.getId();
		List<Vote> votes = schedule.getVotes();
		
		for (Vote vote : votes) {
			
			if (clientId.equals(vote.getClient().getId())) {
				throw new BadRequestException("O cliente já votou nesta pauta.");
			}
		}
	}
}
