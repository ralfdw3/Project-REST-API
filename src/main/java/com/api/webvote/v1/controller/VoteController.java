package com.api.webvote.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.webvote.v1.model.Vote;
import com.api.webvote.v1.service.VoteServiceInterface;

import jakarta.validation.Valid;

@RequestMapping("v1/api/vote")
@RestController
public class VoteController {

	@Autowired
	private VoteServiceInterface voteServiceInterface;

	@PostMapping(path = "/new")
	public ResponseEntity<Vote> newVote (@Valid @RequestBody Vote vote) {
		
		return voteServiceInterface.save(vote);
	}

}
