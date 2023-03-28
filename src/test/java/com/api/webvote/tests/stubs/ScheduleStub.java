package com.api.webvote.tests.stubs;

import com.api.webvote.v1.model.Schedule;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ScheduleStub {

    static Schedule scheduleDefault(){
        return new Schedule(1L, "Schedule title", new ArrayList<>(), 1, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1));
    }
}
