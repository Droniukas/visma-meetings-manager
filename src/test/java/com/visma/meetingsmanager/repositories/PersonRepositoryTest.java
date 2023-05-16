package com.visma.meetingsmanager.repositories;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.visma.meetingsmanager.models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class PersonRepositoryTest {

    @Autowired
    private PersonRepository underTest;

    @Value("${person.file.path}")
    private String PERSON_TEST_FILEPATH;

    private final ObjectMapper objectMapper = getObjectMapper();

    @AfterEach
    void tearDown() throws IOException {
        ArrayList<Object> emptyList = new ArrayList<>();
        FileWriter peopleTestFileWriter = new FileWriter(PERSON_TEST_FILEPATH);
        objectMapper.writeValue(peopleTestFileWriter, emptyList);
    }

    @Test
    void getAll() throws IOException {
        List<Person> expectedPeople = List.of(new Person(), new Person());

        FileWriter peopleTestFileWriter = new FileWriter(PERSON_TEST_FILEPATH);
        objectMapper.writeValue(peopleTestFileWriter, expectedPeople);

        assertThat(expectedPeople).isEqualTo(underTest.getAll());
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