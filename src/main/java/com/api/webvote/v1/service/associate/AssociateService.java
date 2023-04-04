package com.api.webvote.v1.service.associate;

import com.api.webvote.v1.config.feign.CpfValidatorUsingFeignImp;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.repository.AssociateRepository;
import com.api.webvote.v1.service.associate.check.AssociateSystemChecker;
import com.api.webvote.v1.service.associate.check.CheckDuplicateCpf;
import com.api.webvote.v1.service.associate.check.CpfValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociateService implements AssociateServiceInterface {

	private final AssociateRepository associateRepository;
	private final CpfValidatorUsingFeignImp cpfValidatorUsingFeign;

	@Autowired
	public AssociateService(AssociateRepository associateRepository, CpfValidatorUsingFeignImp cpfValidatorUsingFeign) {
		this.associateRepository = associateRepository;
		this.cpfValidatorUsingFeign = cpfValidatorUsingFeign;
	}

	@Autowired
	private List<AssociateSystemChecker> associateSystemChecker;

	@Transactional
	@Override
	public ResponseEntity<Associate> save(Associate associate) {
		//cpfValidatorUsingFeign.validate(cpf);
		//Problema de versão entre FeignClient com SpringBoot
		//Decidi deixar desabilitado, visto que seria utilizado apenas com intuito de aprendizado :)

		associateSystemChecker.forEach(c -> c.check(associate));

		Associate savedAssociate = associateRepository.save(associate);
		return ResponseEntity.ok(savedAssociate);
	}

	@Override
	public ResponseEntity<Associate> get(Long id) {
		return associateRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

}
