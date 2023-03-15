package com.api.webvote.v1.service.schedule;

import java.io.IOException;

import com.api.webvote.v1.model.Schedule;
import com.api.webvote.v1.repository.ScheduleRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ScheduleDeserializer extends JsonDeserializer<Schedule> {

    private ScheduleRepository scheduleRepository;

    public ScheduleDeserializer(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Long id = jsonParser.getLongValue();
        return scheduleRepository.findById(id).orElseThrow(() -> new JsonParseException(jsonParser, "Schedule not found with ID " + id));
    }
}

