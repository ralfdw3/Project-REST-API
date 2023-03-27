package com.api.webvote.v1.controller;

import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.service.associate.AssociateServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/api/associate")
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
