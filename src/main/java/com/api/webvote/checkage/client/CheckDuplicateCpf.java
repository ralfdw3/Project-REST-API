package com.api.webvote.checkage.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.webvote.controller.ClientController;
import com.api.webvote.exception.BadRequestException;
import com.api.webvote.model.Client;

public class CheckDuplicateCpf implements ClientChecker {

	final Logger logger = LoggerFactory.getLogger(ClientController.class);
	private List<Client> clients;
	private String cpf;

	public CheckDuplicateCpf(List<Client> clients, String cpf) {
		this.clients = clients;
		this.cpf = cpf;
	}

	@Override
	public void check() {
		logger.debug("-> Verificando a existencia de CPF duplicado no banco de dados.");
		
		for (Client c : clients) {
			String cpf = c.getCpf();

			if (this.cpf.equals(cpf)) {
				throw new BadRequestException("Este CPF já está sendo utilizado");
			}
		}
	}
	
}
