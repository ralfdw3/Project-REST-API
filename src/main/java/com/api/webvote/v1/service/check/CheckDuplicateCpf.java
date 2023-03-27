package com.api.webvote.v1.service.check;

import com.api.webvote.v1.controller.AssociateController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.repository.AssociateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckDuplicateCpf {

    final static Logger logger = LoggerFactory.getLogger(AssociateController.class);

    public static void validate(String cpf, AssociateRepository associateRepository) {
        logger.debug("-> Verificando a existencia de CPF duplicado no banco de dados.");

		Associate associate  = associateRepository.findByCpf(cpf);

        if (associate != null)
            throw new BadRequestException("Este CPF já está sendo utilizado");
    }
}
