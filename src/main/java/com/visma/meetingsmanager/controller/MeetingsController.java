package com.visma.meetingsmanager.controller;

import com.visma.meetingsmanager.dto.MeetingDto;
import com.visma.meetingsmanager.model.Meeting;
import com.visma.meetingsmanager.service.MeetingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.visma.meetingsmanager.MeetingsManagerApplication.BASE_URL;

@RestController
@RequestMapping(value = BASE_URL + "/meetings")
public class MeetingsController {

    @Autowired
    MeetingsService meetingsService;
    @PostMapping("/create")
    public ResponseEntity<Meeting> createNewMeeting(@RequestBody Meeting meeting) {
        try {
            Meeting newMeeting = meetingsService.createNewMeeting(meeting);
            return ResponseEntity.ok(newMeeting);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get")
    public ResponseEntity<MeetingDto[]> getMeetings() {
        return meetingsService.getMeetings().map(meetingDto -> ResponseEntity.ok(meetingDto));
    }
}
