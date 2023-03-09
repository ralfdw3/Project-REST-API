package com.api.webvote.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String vote;
	
	@Column(nullable = false)
	private Long clientId;
	
	@Column(nullable = false)
	private Long scheduleId;
	
	public Vote(Long id, String vote, Long clientId, Long scheduleId) {
		this.id = id;
		this.vote = vote;
		this.clientId = clientId;
		this.scheduleId = scheduleId;
	}
	
	public Vote() {
		super();
	}

	public String getVote() {
		return vote;
	}

	public Long getClientId() {
		return clientId;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setVote(String vote) {
		this.vote = vote;
	}

}
