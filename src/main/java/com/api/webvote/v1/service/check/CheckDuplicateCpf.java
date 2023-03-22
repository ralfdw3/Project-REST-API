package com.api.webvote.v1.service.check;

import java.util.List;

import com.api.webvote.v1.repository.AssociateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.webvote.v1.controller.AssociateController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Associate;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckDuplicateCpf {

    private static AssociateRepository associateRepository;
    final static Logger logger = LoggerFactory.getLogger(AssociateController.class);

    public static void validate(Associate associate) {
        logger.debug("-> Verificando a existencia de CPF duplicado no banco de dados.");

		List<Associate> list = associateRepository.findByCpf(associate.getCpf());

        if (!list.isEmpty())
            throw new BadRequestException("Este CPF já está sendo utilizado");
    }
}
