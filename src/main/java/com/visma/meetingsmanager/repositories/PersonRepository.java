package com.visma.meetingsmanager.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.visma.meetingsmanager.models.Person;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    private final ObjectMapper objectMapper = getObjectMapper();

    private final String FILENAME = "src/main/resources/json/people.json";

    public List<Person> getAll() {
        try {
            FileReader reader = new FileReader(FILENAME);
            return objectMapper.readValue(reader, new TypeReference<List<Person>>() {
            });
        } catch (IOException e) {
            System.out.println("Could not read from person.json file: " + e.getMessage());
            return new ArrayList<Person>();
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
