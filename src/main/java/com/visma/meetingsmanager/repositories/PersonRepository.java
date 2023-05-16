package com.visma.meetingsmanager.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.visma.meetingsmanager.exceptions.ApiRequestException;
import com.visma.meetingsmanager.models.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Repository
public class PersonRepository {

    private final ObjectMapper objectMapper = getObjectMapper();

    @Value("${person.file.path}")
    private String PERSON_FILEPATH;

    public List<Person> getAll() {
        try {
            FileReader reader = new FileReader(PERSON_FILEPATH);
            return objectMapper.readValue(reader, new TypeReference<List<Person>>() {
            });
        } catch (IOException e) {
            throw new ApiRequestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private ObjectMapper getObjectMapper() {
        return JsonMapper.builder()
                .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build().registerModule(new JavaTimeModule());
    }
}
