package com.api.webvote.v1.controller;

import com.api.webvote.v1.model.Vote;
import com.api.webvote.v1.service.vote.VoteServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("v1/api/vote")
@RestController
public class VoteController {

	private final VoteServiceInterface voteServiceInterface;
	@Autowired
	public VoteController(VoteServiceInterface voteServiceInterface) {
		this.voteServiceInterface = voteServiceInterface;
	}

	@PostMapping
	public ResponseEntity<Vote> newVote (@Valid @RequestBody Vote vote) {
		return voteServiceInterface.save(vote);
	}

}
