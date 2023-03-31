package com.api.webvote.v1.service.associate;

import com.api.webvote.v1.config.feign.CpfValidatorUsingFeign;
import com.api.webvote.v1.config.feign.CpfValidatorUsingFeignImp;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.repository.AssociateRepository;
import com.api.webvote.v1.service.check.CheckDuplicateCpf;
import com.api.webvote.v1.service.check.CpfValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AssociateService implements AssociateServiceInterface {

	private final AssociateRepository associateRepository;
	private final CpfValidatorUsingFeignImp cpfValidatorUsingFeign;

	@Autowired
	public AssociateService(AssociateRepository associateRepository, CpfValidatorUsingFeignImp cpfValidatorUsingFeign) {
		this.associateRepository = associateRepository;
		this.cpfValidatorUsingFeign = cpfValidatorUsingFeign;
	}

	@Transactional
	@Override
	public ResponseEntity<Associate> save(Associate associate) {
		String cpf = associate.getCpf();

		//cpfValidatorUsingFeign.validate(cpf);
		//Problema de versão entre FeignClient com SpringBoot
		//Decidi deixar desabilitado, visto que seria utilizado apenas com intuito de aprendizado :)
		CpfValidator.validate(cpf);
		CheckDuplicateCpf.validate(cpf, associateRepository);

		Associate savedAssociate = associateRepository.save(associate);
		return ResponseEntity.ok(savedAssociate);
	}

	@Override
	public ResponseEntity<Associate> get(Long id) {
		return associateRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

}
