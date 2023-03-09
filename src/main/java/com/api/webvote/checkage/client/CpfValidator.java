package com.api.webvote.checkage.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.webvote.controller.ClientController;
import com.api.webvote.exception.BadRequestException;

public class CpfValidator implements ClientChecker {

	final Logger logger = LoggerFactory.getLogger(ClientController.class);
	private String cpf;

	public CpfValidator(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public void check() {
		logger.debug("-> Verificando se o CPF é válido.");
		
		cpf = cpf.replaceAll("[^0-9]", "");

		if (cpf.length() != 11) {
			throw new BadRequestException("O CPF deve conter 11 dígitos.");
		}

		int digit1 = calcDigit(cpf.substring(0, 9), 10);
		int digit2 = calcDigit(cpf.substring(0, 9) + digit1, 11);

		if (!cpf.equals(cpf.substring(0, 9) + digit1 + digit2))
			throw new BadRequestException("CPF inválido.");

	}

	private static int calcDigit(String str, int weight) {
		int sum = 0;
		for (int i = 0; i < str.length(); i++) {
			sum += Integer.parseInt(str.substring(i, i + 1)) * weight--;
		}
		int rest = sum % 11;
		return rest < 2 ? 0 : 11 - rest;
	}

}
