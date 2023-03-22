package com.api.webvote.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.service.associate.AssociateServiceInterface;

import jakarta.validation.Valid;

@RequestMapping("v1/api/client")
@RestController
public class AssociateController {

	private final AssociateServiceInterface associateServiceInterface;
	
	@Autowired
	public AssociateController(AssociateServiceInterface associateServiceInterface) {
		this.associateServiceInterface = associateServiceInterface;
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Associate> associateById(@PathVariable("id") Long id) {
		return associateServiceInterface.get(id);
	}

	@PostMapping
	public ResponseEntity<Associate> newAssociate (@Valid @RequestBody Associate associate) {
		return associateServiceInterface.save(associate);
		
	}

}
