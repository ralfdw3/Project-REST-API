package com.api.webvote.tests.stubs;

import com.api.webvote.v1.enums.VotoEnum;
import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.model.Vote;

public interface VoteStub {

    static Vote voteDefault(){
        Associate associateDefault = AssociateStub.associateDefault();
        Schedule scheduleDefault = ScheduleStub.scheduleDefault();
        return new Vote(1L, VotoEnum.SIM, associateDefault, scheduleDefault);
    }
}
