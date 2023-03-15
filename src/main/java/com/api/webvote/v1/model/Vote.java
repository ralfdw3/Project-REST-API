package com.api.webvote.v1.model;

import com.api.webvote.v1.enums.VotoEnum;
import com.api.webvote.v1.service.client.ClientDeserializer;
import com.api.webvote.v1.service.schedule.ScheduleDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Vote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VotoEnum vote;
	
	@JsonDeserialize(using = ClientDeserializer.class)
	@OneToOne
    @JoinColumn(name = "client_id", nullable = false)
	private Client client;
	
	@JsonDeserialize(using = ScheduleDeserializer.class)
	@OneToOne
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;
	
	public Vote() {
		super();
	}
	
	public Vote(Long id, VotoEnum valor, Client client, Schedule schedule) {
		this.id = id;
		this.vote = valor;
		this.client = client;
		this.schedule = schedule;
	}

	public Long getId() {
		return id;
	}

	public VotoEnum getVote() {
		return vote;
	}

	public Client getClient() {
		return client;
	}

	public Schedule getSchedule() {
		return schedule;
	}
	
	
}
