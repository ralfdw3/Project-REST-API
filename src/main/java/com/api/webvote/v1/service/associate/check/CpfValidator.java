package com.api.webvote.v1.service.associate.check;

import com.api.webvote.v1.controller.AssociateController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Associate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CpfValidator implements AssociateSystemChecker {
    final static Logger logger = LoggerFactory.getLogger(AssociateController.class);
    @Override
    public void check(Associate associate) {
        logger.debug("-> Verificando se o CPF é válido.");

        String cpf = associate.getCpf().replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            logger.debug("-> cpf.length(): " + cpf.length());
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
