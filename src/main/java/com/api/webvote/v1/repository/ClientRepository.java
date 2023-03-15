package com.api.webvote.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.webvote.v1.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
