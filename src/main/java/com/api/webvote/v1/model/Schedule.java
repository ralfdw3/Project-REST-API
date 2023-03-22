package com.api.webvote.v1.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;

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
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
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
}
