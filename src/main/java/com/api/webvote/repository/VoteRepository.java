package com.api.webvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.webvote.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{

}
