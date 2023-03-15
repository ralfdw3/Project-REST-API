package com.api.webvote.v1.service.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.webvote.v1.controller.ClientController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Client;

public class CheckDuplicateCpf {

	final static Logger logger = LoggerFactory.getLogger(ClientController.class);

	public static void validate(Client client, List<Client> clients) {
		logger.debug("-> Verificando a existencia de CPF duplicado no banco de dados.");
		
		for (Client c : clients) {

			if (client.getCpf().equals(c.getCpf())) {
				throw new BadRequestException("Este CPF já está sendo utilizado");
			}
		}
	}
}
