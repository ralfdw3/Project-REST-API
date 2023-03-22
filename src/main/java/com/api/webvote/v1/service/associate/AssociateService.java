package com.api.webvote.v1.service.associate;

import com.api.webvote.v1.service.check.CheckDuplicateCpf;
import com.api.webvote.v1.service.check.CpfValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.repository.AssociateRepository;

import jakarta.transaction.Transactional;

@Service
public class AssociateService implements AssociateServiceInterface {

	private final AssociateRepository associateRepository;

	@Autowired
	public AssociateService(AssociateRepository associateRepository) {
		this.associateRepository = associateRepository;
	}

	@Transactional
	@Override
	public ResponseEntity<Associate> save(Associate associate) {

		CheckDuplicateCpf.validate(associate);
		CpfValidator.validate(associate);

		associateRepository.save(associate);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Associate> get(Long id) {
		return associateRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

}
