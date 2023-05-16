package com.visma.meetingsmanager.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.visma.meetingsmanager.models.Meeting;
import com.visma.meetingsmanager.models.MeetingPerson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class MeetingRepositoryTest {

    @Autowired
    private MeetingRepository underTest;

    @Value("${meetings.file.path}")
    private String MEETINGS_TEST_FILEPATH;

    @Value("${meetingsPeople.file.path}")
    private String MEETINGS_PEOPLE_TEST_FILEPATH;

    private final ObjectMapper objectMapper = getObjectMapper();

    @AfterEach
    void tearDown() throws IOException {
        ArrayList<Object> emptyList = new ArrayList<>();
        FileWriter meetingsTestFileWriter = new FileWriter(MEETINGS_TEST_FILEPATH);
        FileWriter meetingsPeopleTestFileWriter = new FileWriter(MEETINGS_PEOPLE_TEST_FILEPATH);
        objectMapper.writeValue(meetingsTestFileWriter, emptyList);
        objectMapper.writeValue(meetingsPeopleTestFileWriter, emptyList);
    }

    @Test
    void itShouldGetAll() throws IOException {
        List<Meeting> expectedMeetings = List.of(new Meeting(), new Meeting());

        FileWriter meetingsTestFileWriter = new FileWriter(MEETINGS_TEST_FILEPATH);
        objectMapper.writeValue(meetingsTestFileWriter, expectedMeetings);

        assertThat(expectedMeetings).isEqualTo(underTest.getAll());
    }

    @Test
    void itShouldSaveAll() throws IOException {
        List<Meeting> expectedMeetings = List.of(new Meeting(), new Meeting());
        underTest.saveAll(expectedMeetings);

        FileReader meetingsTestFileReader = new FileReader(MEETINGS_TEST_FILEPATH);
        List<Meeting> actualMeetings = objectMapper.readValue(meetingsTestFileReader, new TypeReference<>() {
        });

        assertThat(expectedMeetings).isEqualTo(actualMeetings);
    }

    @Test
    void itShouldGetAllMeetingsPeopleRelationships() throws IOException {
        List<MeetingPerson> expectedMeetingsPeople = List.of(new MeetingPerson(), new MeetingPerson());

        FileWriter meetingsPeopleTestFileWriter = new FileWriter(MEETINGS_PEOPLE_TEST_FILEPATH);
        objectMapper.writeValue(meetingsPeopleTestFileWriter, expectedMeetingsPeople);

        assertThat(expectedMeetingsPeople).isEqualTo(underTest.getAllMeetingsPeopleRelationships());
    }

    @Test
    void itShouldSaveAllMeetingsPeopleRelationships() throws IOException {
        List<MeetingPerson> expectedMeetingsPeople = List.of(new MeetingPerson(), new MeetingPerson());
        underTest.saveAllMeetingsPeopleRelationships(expectedMeetingsPeople);

        FileReader meetingsTestFileReader = new FileReader(MEETINGS_PEOPLE_TEST_FILEPATH);
        List<MeetingPerson> actualMeetingsPeople = objectMapper.readValue(meetingsTestFileReader, new TypeReference<>() {
        });

        assertThat(expectedMeetingsPeople).isEqualTo(actualMeetingsPeople);
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