package com.api.webvote.v1.repository;

import com.api.webvote.v1.model.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, Long>{

    Associate findByCpf(String cpf);
}
