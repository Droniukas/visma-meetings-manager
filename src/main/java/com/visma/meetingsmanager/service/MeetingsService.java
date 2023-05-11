package com.visma.meetingsmanager.service;

import com.visma.meetingsmanager.dto.MeetingDto;
import com.visma.meetingsmanager.model.Meeting;
import com.visma.meetingsmanager.repository.MeetingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MeetingsService {

    @Autowired
    private MeetingsRepository meetingsRepository;

    public List<Meeting> getMeetings() {
        return meetingsRepository.getAll();
    }

    public List<Meeting> createNewMeeting(Meeting meeting) {
        List<Meeting> meetings = meetingsRepository.getAll();
        meetings.add(meeting);
        meetingsRepository.saveAll(meetings);
        return meetings;
    }

}
