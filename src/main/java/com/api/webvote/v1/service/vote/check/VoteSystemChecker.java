package com.api.webvote.v1.service.vote.check;

import com.api.webvote.v1.model.Vote;

public interface VoteSystemChecker {

    void check(Vote vote);
}
