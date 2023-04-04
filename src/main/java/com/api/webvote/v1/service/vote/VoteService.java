package com.api.webvote.v1.service.vote;

import com.api.webvote.v1.model.Vote;
import com.api.webvote.v1.repository.VoteRepository;
import com.api.webvote.v1.service.vote.check.VoteSystemChecker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService implements VoteServiceInterface {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Autowired
    private List<VoteSystemChecker> checkBeforeVote;

    @Transactional
    @Override
    public ResponseEntity<Vote> save(Vote vote) {
        checkBeforeVote.forEach(c -> c.check(vote));

        vote.getSchedule().getVotes().add(vote);
        voteRepository.save(vote);

        return ResponseEntity.ok().build();
    }
}

