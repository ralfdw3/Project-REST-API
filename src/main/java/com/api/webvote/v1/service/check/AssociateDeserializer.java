package com.api.webvote.v1.service.check;

import java.io.IOException;

import com.api.webvote.v1.model.Associate;
import com.api.webvote.v1.repository.AssociateRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class AssociateDeserializer{ /*extends JsonDeserializer<Associate> {

    private AssociateRepository associateRepository;

    public AssociateDeserializer(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;
    }

    @Override
    public Associate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Long id = jsonParser.getLongValue();
        return associateRepository.findById(id).orElseThrow(() -> new JsonParseException(jsonParser, "Schedule not found with ID " + id));
    }*/
}

