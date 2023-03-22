package com.api.webvote.v1.service.associate;

import org.springframework.http.ResponseEntity;

import com.api.webvote.v1.model.Associate;

public interface AssociateServiceInterface {
	
	ResponseEntity<Associate> save (Associate client);
	ResponseEntity<Associate> get (Long id);

}
