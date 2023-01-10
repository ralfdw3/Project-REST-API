package com.api.webvote.controller;

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

import com.api.webvote.model.Client;
import com.api.webvote.repository.ClientRepository;

@RestController
public class ClientController {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
	
	@Autowired
	private ClientRepository clientRepository;
	
	@GetMapping(path = "/api/client/{id}")
	public ResponseEntity<Client> clientById (@PathVariable("id") Long id)
	{
		return clientRepository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
						.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping(path = "/api/client/save")
	public ResponseEntity<Client> save (@RequestBody Client client) throws Exception
	{
		String clientCpf = client.getCpf();
		
		/* Verifica se o CPF é válido - Tarefa extra 1
		 EU faria desta maneira, porém a URL está off
		
		VoterVerifier voterVerifier = new VoterVerifier();
		boolean canVote = voterVerifier.canVote(clientCpf);
		
		if (canVote == false){
			logger.debug("-> O CPF do cliente " + client.getName() + " é inválido.");
			return ResponseEntity.badRequest().build();
		}*/
		
		// Verifica se o CPF já está cadastrado
		List<Client> clients = clientRepository.findAll();
		
		for (Client c : clients) {
			String cpf = c.getCpf();
			
			if (clientCpf.equals(cpf)) {
				logger.debug("-> O cliente " + client.getName() + " não foi cadastrado, pois o CPF já está sendo utilizado.");
				return ResponseEntity.badRequest().build();
			}
		}
		
		// Salva o registro do cliente no banco de dados
		logger.debug("-> O cliente " + client.getName() + " foi criado e adicionado no banco de dados.");
		clientRepository.save(client);
		
		return ResponseEntity.ok(client);
	}
	
}
