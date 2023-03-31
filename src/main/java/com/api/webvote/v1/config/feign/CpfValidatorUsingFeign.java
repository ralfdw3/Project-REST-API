package com.api.webvote.v1.config.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cpf", url = "https://api.cpfcnpj.com.br")
public interface CpfValidatorUsingFeign {

    @GetMapping(value = "/5ae973d7a997af13f0aaf2bf60e65803/9/{cpf}")
    String validate(@PathVariable("cpf") String cpf);
}
