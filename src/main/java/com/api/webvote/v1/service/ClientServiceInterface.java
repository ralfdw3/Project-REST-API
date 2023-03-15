package com.api.webvote.v1.service;

import org.springframework.http.ResponseEntity;

import com.api.webvote.v1.model.Client;

public interface ClientServiceInterface {
	
	ResponseEntity<Client> save (Client client);
	ResponseEntity<Client> get (Long id);

}
