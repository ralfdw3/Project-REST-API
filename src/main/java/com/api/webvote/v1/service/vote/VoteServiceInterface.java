package com.api.webvote.v1.service.vote;

import com.api.webvote.v1.model.Vote;
import org.springframework.http.ResponseEntity;

public interface VoteServiceInterface {
	
	public ResponseEntity<Vote> save(Vote vote);

}
