package com.visma.meetingsmanager.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.visma.meetingsmanager.exceptions.ApiRequestException;
import com.visma.meetingsmanager.models.Meeting;
import com.visma.meetingsmanager.models.MeetingPerson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Repository
public class MeetingRepository {

    private final ObjectMapper objectMapper = getObjectMapper();

    @Value("${meetings.file.path}")
    private String MEETINGS_FILEPATH;

    @Value("${meetingsPeople.file.path}")
    private String MEETINGS_PERSON_FILEPATH;

    public List<Meeting> getAll() {
        try {
            FileReader reader = new FileReader(MEETINGS_FILEPATH);
            return objectMapper.readValue(reader, new TypeReference<List<Meeting>>() {
            });
        } catch (IOException e) {
            throw new ApiRequestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void saveAll(List<Meeting> meetings) {
        try {
            FileWriter writer = new FileWriter(MEETINGS_FILEPATH);
            objectMapper.writeValue(writer, meetings);
        } catch (IOException e) {
            throw new ApiRequestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public List<MeetingPerson> getAllMeetingsPeopleRelationships() {
        try {
            FileReader reader = new FileReader(MEETINGS_PERSON_FILEPATH);
            return objectMapper.readValue(reader, new TypeReference<List<MeetingPerson>>() {
            });
        } catch (IOException e) {
            throw new ApiRequestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void saveAllMeetingsPeopleRelationships(List<MeetingPerson> meetingsPeople) {
        try {
            FileWriter writer = new FileWriter(MEETINGS_PERSON_FILEPATH);
            objectMapper.writeValue(writer, meetingsPeople);
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
