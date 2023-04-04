package com.api.webvote.tests.stubs;

import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface ScheduleStub {

    static Schedule scheduleDefault(){
        return new Schedule(1L, "Schedule title", new ArrayList<>(), 1, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1));
    }

    static Schedule scheduleExpired(){
        return new Schedule(1L, "Schedule title", new ArrayList<>(), 1, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(-5));
    }

    static Schedule scheduleWithVoteFromAssociateDefault(){
        List<Vote> votes = new ArrayList<Vote>();
        votes.add(VoteStub.voteDefault());

        return new Schedule(1L, "Schedule title", votes, 1, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1));
    }
}
