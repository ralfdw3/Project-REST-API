package com.api.webvote.v1.service.associate.check;

import com.api.webvote.v1.controller.AssociateController;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.repository.AssociateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckDuplicateCpf implements AssociateSystemChecker {
    private AssociateRepository associateRepository;
    @Autowired
    public CheckDuplicateCpf(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;
    }

    final static Logger logger = LoggerFactory.getLogger(AssociateController.class);

    @Override
    public void check(Associate associate) {
        logger.debug("-> Verificando a existencia de CPF duplicado no banco de dados.");

        Associate checkAssociate = associateRepository.findByCpf(associate.getCpf());

        if (checkAssociate != null)
            throw new BadRequestException("Este CPF já está sendo utilizado");

    }
}
