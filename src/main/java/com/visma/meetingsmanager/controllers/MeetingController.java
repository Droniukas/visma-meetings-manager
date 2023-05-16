package com.visma.meetingsmanager.controllers;

import com.visma.meetingsmanager.dtos.MeetingDto;
import com.visma.meetingsmanager.dtos.MeetingPersonDto;
import com.visma.meetingsmanager.enums.MeetingCategory;
import com.visma.meetingsmanager.enums.MeetingType;
import com.visma.meetingsmanager.models.Meeting;
import com.visma.meetingsmanager.services.MeetingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.visma.meetingsmanager.MeetingsManagerApplication.BASE_URL;

@RestController
@Validated
@RequestMapping(value = BASE_URL + "/meetings")
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public MeetingDto createNewMeeting(@RequestBody @Valid Meeting newMeeting) {
        return meetingService.createNewMeeting(newMeeting);
    }

    @DeleteMapping("/delete")
    public MeetingDto deleteMeetingByName(
            @RequestHeader("person") String person,
            @RequestParam(value = "name") String name
    ) {
        return meetingService.deleteMeetingByName(name, person);
    }

    @GetMapping("/get")
    public List<MeetingDto> getMeetings(
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "responsiblePersonName", required = false) String responsiblePersonName,
            @RequestParam(value = "category", required = false) String meetingCategory,
            @RequestParam(value = "type", required = false) String meetingType,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "numberOfParticipants", required = false) @Min(1) Integer numberOfParticipants
    ) {
        return meetingService.getMeetings(
                description,
                responsiblePersonName,
                MeetingCategory.valueOfLabel(meetingCategory),
                MeetingType.valueOfLabel(meetingType),
                startDate,
                endDate,
                numberOfParticipants
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addPersonToMeeting")
    public MeetingPersonDto addPersonToMeeting(
            @RequestParam(value = "personName") String personName,
            @RequestParam(value = "meetingName") String meetingName
    ) {
        return meetingService.addNewMeetingPersonRelationship(meetingName, personName);
    }

    @DeleteMapping("/removePersonFromMeeting")
    public MeetingPersonDto removePersonFromMeeting(
            @RequestParam(value = "personName") String personName,
            @RequestParam(value = "meetingName") String meetingName
    ) {
        return meetingService.removeMeetingPersonRelationship(meetingName, personName);
    }
}
