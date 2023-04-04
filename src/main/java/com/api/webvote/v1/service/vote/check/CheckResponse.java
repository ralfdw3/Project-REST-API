package com.api.webvote.v1.service.vote.check;

import com.api.webvote.v1.controller.VoteController;
import com.api.webvote.v1.enums.VotoEnum;
import com.api.webvote.v1.exception.BadRequestException;
import com.api.webvote.v1.model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CheckResponse implements VoteSystemChecker {
    final static Logger logger = LoggerFactory.getLogger(VoteController.class);
    @Override
    public void check(Vote vote) {
        logger.debug("-> Verificando se a resposta do cliente foi 'SIM'/'NAO'.");

        VotoEnum value = vote.getVote();

        if (value != VotoEnum.SIM && value != VotoEnum.NAO)
            throw new BadRequestException("Resposta inválida.");

    }
}

