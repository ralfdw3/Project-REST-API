package com.api.webvote.v1.service;

import org.springframework.http.ResponseEntity;

import com.api.webvote.v1.model.Vote;

public interface VoteServiceInterface {
	
	public ResponseEntity<Vote> save(Vote vote);

}
