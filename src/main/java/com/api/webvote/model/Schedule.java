package com.api.webvote.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
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
	private int durationTime;
	
	@Column
	private Timestamp startTime;
	
	@Column
	private Timestamp endTime;
	
	@Column
	private int yesVotesCount = 0;
	
	@Column
	private int noVotesCount = 0;

	public Schedule(Long id, String title, Long clientId, int durationTime, Timestamp startTime, Timestamp endTime,
			int yesVotesCount, int noVotesCount) {
		super();
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
		super();
		this.title = title;
		this.clientId = clientId;
	}
}
