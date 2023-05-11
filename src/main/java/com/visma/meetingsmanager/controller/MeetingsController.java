package com.visma.meetingsmanager.controller;

import com.visma.meetingsmanager.dto.MeetingDto;
import com.visma.meetingsmanager.model.Meeting;
import com.visma.meetingsmanager.service.MeetingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.visma.meetingsmanager.MeetingsManagerApplication.BASE_URL;

@RestController
@RequestMapping(value = BASE_URL + "/meetings")
public class MeetingsController {

    @Autowired
    MeetingsService meetingsService;
    @PostMapping("/create")
    public ResponseEntity<List<Meeting>> createNewMeeting(@RequestBody Meeting meeting) {
        try {
            List<Meeting> newMeetings = meetingsService.createNewMeeting(meeting);
            return ResponseEntity.ok(newMeetings);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<Meeting>> getMeetings() {
        return ResponseEntity.ok(meetingsService.getMeetings());
    }
}
