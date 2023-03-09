package com.api.webvote.checkage.vote;

import com.api.webvote.model.Schedule;
import com.api.webvote.model.Vote;

public class CountVotes implements VoteChecker{
	
	private Vote vote;
	private Schedule schedule;

	public CountVotes(Vote vote, Schedule schedule) {
		this.vote = vote;
		this.schedule = schedule;
	}

	@Override
	public void execute() {
		if (vote.getVote().equals("Sim")) {
			schedule.setYesVotesCount(schedule.getYesVotesCount() + 1);
		} else {
			schedule.setNoVotesCount(schedule.getNoVotesCount() + 1);
		}
	}

}
