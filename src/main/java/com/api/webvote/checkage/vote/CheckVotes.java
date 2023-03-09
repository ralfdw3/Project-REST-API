package com.api.webvote.checkage.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.webvote.controller.VoteController;
import com.api.webvote.exception.BadRequestException;
import com.api.webvote.model.Client;
import com.api.webvote.model.Vote;

public class CheckVotes implements VoteChecker {

	private Vote vote;
	private Client client;

	public CheckVotes(Client client, Vote vote) {
		this.client = client;
		this.vote = vote;
	}

	@Override
	public void execute() {
		final Logger logger = LoggerFactory.getLogger(VoteController.class);
		logger.debug("-> Verificando se este cliente já votou nesta pauta.");

		String votedSchedules = client.getVotedSchedules();

		if (votedSchedules != null && !votedSchedules.isEmpty()) {
			for (String s : votedSchedules.split(";")) {

				if (Long.valueOf(s) == vote.getScheduleId())
					throw new BadRequestException("O cliente já votou nesta pauta.");
			}
		}
	}

}
