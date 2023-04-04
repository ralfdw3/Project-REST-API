package com.api.webvote.tests.stubs;

import com.api.webvote.v1.enums.VotoEnum;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;

import java.time.LocalDateTime;

public interface VoteStub {
    Associate associateDefault = AssociateStub.associateDefault();
    Schedule scheduleDefault = ScheduleStub.scheduleDefault();
    Schedule scheduleExpired = ScheduleStub.scheduleExpired();


    static Vote voteDefault(){
        return new Vote(1L, VotoEnum.SIM, associateDefault, scheduleDefault);
    }

    static Vote voteWithVoteNull(){
        return new Vote(1L, null, associateDefault, scheduleDefault);
    }

    static Vote voteWithScheduleExpired(){
        return new Vote(1L, null, associateDefault, scheduleExpired);
    }

    static Vote voteWhenAssociateAlreadyVoted(){
        return new Vote(1L, null, associateDefault, ScheduleStub.scheduleWithVoteFromAssociateDefault());
    }
}
