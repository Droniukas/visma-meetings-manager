package com.visma.meetingsmanager.service;

import com.visma.meetingsmanager.dto.MeetingDto;
import com.visma.meetingsmanager.model.Meeting;
import com.visma.meetingsmanager.repository.MeetingsRepository;
import org.springframework.stereotype.Service;

@Service

public class MeetingsService {

    private MeetingsRepository meetingsRepository;

    public MeetingDto[] getMeetings() {
        return meetingsRepository.getMeetings();
    }

    public Meeting createNewMeeting(Meeting meeting) {
        return meetingsRepository.createNewMeeting(meeting);
    }

}
