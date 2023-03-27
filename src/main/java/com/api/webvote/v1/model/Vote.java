package com.api.webvote.v1.model;

import com.api.webvote.v1.enums.VotoEnum;
import jakarta.persistence.*;

@Entity
public class Vote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VotoEnum vote;
	
	@OneToOne
    @JoinColumn(name = "associate_id", nullable = false)
	private Associate associate;
	
	@OneToOne
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;
	
	public Vote() {
		super();
	}
	
	public Vote(Long id, VotoEnum valor, Associate associate, Schedule schedule) {
		this.id = id;
		this.vote = valor;
		this.associate = associate;
		this.schedule = schedule;
	}

	public Long getId() {
		return id;
	}

	public VotoEnum getVote() {
		return vote;
	}

	public Associate getAssociate() {
		return associate;
	}

	public Schedule getSchedule() {
		return schedule;
	}
	
	
}
