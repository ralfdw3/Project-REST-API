package com.api.webvote.v1.config.feign;

import com.api.webvote.v1.exception.BadRequestException;
import feign.FeignException;
import org.hibernate.service.spi.InjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
public class CpfValidatorUsingFeignImp {
    final static Logger logger = LoggerFactory.getLogger(CpfValidatorUsingFeign.class);
    private final CpfValidatorUsingFeign cpfValidatorUsingFeign;

    public CpfValidatorUsingFeignImp(CpfValidatorUsingFeign cpfValidatorUsingFeign) {
        this.cpfValidatorUsingFeign = cpfValidatorUsingFeign;
    }

    public boolean validate(String cpf) {
        try {
            logger.debug("-> Verificando se o CPF é válido (Feign Client).");

            String situation = cpfValidatorUsingFeign.validate(cpf);
            if (!situation.equals("Regular")){
                throw new BadRequestException("CPF inválido!");
            }
        } catch (FeignException feignException) {
            throw new BadRequestException(feignException.getMessage());
        }
        logger.debug("-> Cpf válido (Feign Client)");
        return true;
    }
}
