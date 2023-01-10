package com.api.webvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.webvote.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	

}
