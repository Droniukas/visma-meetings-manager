package com.visma.meetingsmanager.services;

import com.visma.meetingsmanager.models.Meeting;
import com.visma.meetingsmanager.repositories.MeetingRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MeetingServiceTest {

    @InjectMocks
    private MeetingService underTest;

    @Mock
    MeetingRepository meetingRepository;

    @Mock
    PersonService personService;

    @Test
    void createNewMeeting() {


        Meeting meetingToSave = Instancio.create(Meeting.class);
        meetingToSave.setResponsiblePersonName("Tadas");
        meetingToSave.setStartDate(LocalDateTime.now().plusDays(1));
        meetingToSave.setEndDate(LocalDateTime.now().plusDays(2));
        underTest.createNewMeeting(meetingToSave);

        verify(meetingRepository).saveAll(List.of(meetingToSave));
    }

    @Test
    void deleteMeetingByName() {
    }

    @Test
    void getMeetings() {
    }

    @Test
    void addNewMeetingPersonRelationship() {
    }

    @Test
    void removeMeetingPersonRelationship() {
    }
}