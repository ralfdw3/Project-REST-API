package com.api.webvote.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Long clientId;
	
	@Column
	private int durationTime = 1;
	
	@Column
	private LocalDateTime startTime;
	
	@Column
	private LocalDateTime endTime;
	
	@Column
	private int yesVotesCount = 0;
	
	@Column
	private int noVotesCount = 0;

	public Schedule(Long id, String title, Long clientId, int durationTime, LocalDateTime startTime, LocalDateTime endTime,
			int yesVotesCount, int noVotesCount) {
		this.id = id;
		this.title = title;
		this.clientId = clientId;
		this.durationTime = durationTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.yesVotesCount = yesVotesCount;
		this.noVotesCount = noVotesCount;
	}
	
	public Schedule( String title, Long clientId) {
		this.title = title;
		this.clientId = clientId;
	}
	
	public Schedule() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Long getClientId() {
		return clientId;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public int getYesVotesCount() {
		return yesVotesCount;
	}

	public int getNoVotesCount() {
		return noVotesCount;
	}

	public void setYesVotesCount(int yesVotesCount) {
		this.yesVotesCount = yesVotesCount;
	}

	public void setNoVotesCount(int noVotesCount) {
		this.noVotesCount = noVotesCount;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

}
