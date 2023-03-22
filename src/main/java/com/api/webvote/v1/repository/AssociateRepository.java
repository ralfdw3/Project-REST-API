package com.api.webvote.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.webvote.v1.model.Associate;

import java.util.List;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, Long>{

    List<Associate> findByCpf(String cpf);
}
