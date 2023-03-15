package com.api.webvote.v1.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Client;
import com.api.webvote.v1.repository.ClientRepository;
import com.api.webvote.v1.service.ClientServiceInterface;

import jakarta.transaction.Transactional;

@Service
public class ClientService implements ClientServiceInterface {

	private final ClientRepository clientRepository;

	@Autowired
	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Transactional
	@Override
	public ResponseEntity<Client> save(Client client) {

		try {
			CheckDuplicateCpf.validate(client, clientRepository.findAll());
			CpfValidator.validate(client);

		} catch (BadRequestException e) {
			return ResponseEntity.badRequest().build();

		}
		clientRepository.save(client);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Client> get(Long id) {
		return clientRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

}
