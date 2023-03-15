package com.api.webvote.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.webvote.v1.model.Client;
import com.api.webvote.v1.service.ClientServiceInterface;

import jakarta.validation.Valid;

@RequestMapping("v1/api/client")
@RestController
public class ClientController {

	private final ClientServiceInterface clientServiceInterface;
	
	@Autowired
	public ClientController(ClientServiceInterface clientServiceInterface) {
		this.clientServiceInterface = clientServiceInterface;
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Client> clientById(@PathVariable("id") Long id) {
		return clientServiceInterface.get(id);
	}

	@PostMapping(path = "/new")
	public ResponseEntity<Client> newClient (@Valid @RequestBody Client client) {
		return clientServiceInterface.save(client);
		
	}

}
