package com.api.webvote.checkage.vote;

import com.api.webvote.model.Client;
import com.api.webvote.model.Schedule;

public class AddVotedSchedules implements VoteChecker{
	
	private Client client;
	private Schedule schedule;

	public AddVotedSchedules(Client client, Schedule schedule) {
		this.client = client;
		this.schedule = schedule;
	}

	@Override
	public void execute() {
		String votedSchedules = client.getVotedSchedules();

		if (votedSchedules != null) {
			client.setVotedSchedules(votedSchedules += schedule.getId() + ";");
		} else {
			client.setVotedSchedules(schedule.getId() + ";");
		}
	}

}
