package com.api.webvote.v1.service.associate;

import com.api.webvote.v1.model.Associate;
import org.springframework.http.ResponseEntity;

public interface AssociateServiceInterface {
	
	ResponseEntity<Associate> save (Associate client);
	ResponseEntity<Associate> get (Long id);

}
