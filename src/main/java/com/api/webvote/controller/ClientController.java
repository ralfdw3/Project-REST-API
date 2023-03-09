package com.api.webvote.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.webvote.checkage.client.CheckDuplicateCpf;
import com.api.webvote.checkage.client.ClientChecker;
import com.api.webvote.checkage.client.CpfValidator;
import com.api.webvote.exception.BadRequestException;
import com.api.webvote.model.Client;
import com.api.webvote.repository.ClientRepository;

@RestController
public class ClientController {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

	@Autowired
	private ClientRepository clientRepository;

	@GetMapping(path = "/api/client/{id}")
	public ResponseEntity<Client> clientById(@PathVariable("id") Long id) {
		return clientRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping(path = "/api/client/save")
	public ResponseEntity<Client> save(@RequestBody Client client) {

		List<Client> clients = clientRepository.findAll();
		String cpf = client.getCpf();

		try {
			List<ClientChecker> check = Arrays.asList(
					new CpfValidator(cpf), 
					new CheckDuplicateCpf(clients, cpf));

			check.forEach(obj -> obj.check());

		} catch (BadRequestException e) {
			logger.debug("[ERRO] -> " + e.getMessage());
			return ResponseEntity.badRequest().build();
		}
		
		clientRepository.save(client);
		logger.debug("-> O cliente " + client.getName() + " foi criado e adicionado no banco de dados.");

		return ResponseEntity.ok(client);

	}

}
