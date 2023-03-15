package com.api.webvote.v1.service.client;

import java.io.IOException;

import com.api.webvote.v1.model.Client;
import com.api.webvote.v1.repository.ClientRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ClientDeserializer extends JsonDeserializer<Client> {

    private ClientRepository clientRepository;

    public ClientDeserializer(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Long id = jsonParser.getLongValue();
        return clientRepository.findById(id).orElseThrow(() -> new JsonParseException(jsonParser, "Schedule not found with ID " + id));
    }
}

