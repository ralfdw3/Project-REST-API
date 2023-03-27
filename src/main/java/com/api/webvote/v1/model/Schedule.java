package com.api.webvote.v1.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String title;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "schedule_vote", joinColumns = {
			@JoinColumn(name = "schedule_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "vote_id", referencedColumnName = "id") })
	private List<Vote> votes;
	private int duration;
	private LocalDateTime start = LocalDateTime.now();
	private LocalDateTime end = LocalDateTime.now().plusMinutes(1);

	public Schedule() {
		super();
	}
	
	public Schedule(Long id, String title, List<Vote> votes, int duration, LocalDateTime start, LocalDateTime end) {
		this.id = id;
		this.title = title;
		this.votes = votes;
		this.duration = duration;
		this.start = start;
		this.end = end;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public int getDuration() {
		return duration;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
