package com.api.webvote.model;

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

	public Vote(String vote, Long clientId, Long scheduleId) {
		super();
		this.vote = vote;
		this.clientId = clientId;
		this.scheduleId = scheduleId;
	}
	
	
	
}
