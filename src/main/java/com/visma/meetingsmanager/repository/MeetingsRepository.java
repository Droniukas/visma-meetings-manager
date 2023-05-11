package com.visma.meetingsmanager.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.visma.meetingsmanager.model.Meeting;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MeetingsRepository {

    private final ObjectMapper objectMapper = getObjectMapper();

    private final String fileName = "meetings.json";

    public List<Meeting> getAll(){
        try{
            FileReader reader = new FileReader(fileName);
            return objectMapper.readValue(reader,new TypeReference<List<Meeting>>(){});
        }catch(IOException e){
            System.out.println("Could not read from meetings file: "+e.getMessage());
            return new ArrayList<Meeting>();
        }
    }

    public void saveAll(List<Meeting> meetings){
        try{
            FileWriter writer = new FileWriter(fileName);
            objectMapper.writeValue(writer,meetings);
        }catch(IOException e){
            System.out.println("Could not write to file: "+e.getMessage());
        }
    }

    private ObjectMapper getObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

}
