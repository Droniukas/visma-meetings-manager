package com.visma.meetingsmanager.services;

import com.visma.meetingsmanager.dtos.MeetingDto;
import com.visma.meetingsmanager.dtos.MeetingPersonDto;
import com.visma.meetingsmanager.enums.MeetingCategory;
import com.visma.meetingsmanager.enums.MeetingType;
import com.visma.meetingsmanager.exceptions.ApiRequestException;
import com.visma.meetingsmanager.models.Meeting;
import com.visma.meetingsmanager.models.MeetingPerson;
import com.visma.meetingsmanager.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Service
public class MeetingService {

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    PersonService personService;

    private MeetingDto getMeetingByName(String meetingName) {
        List<Meeting> meetings = meetingRepository.getAll();
        Meeting foundMeeting = meetings.stream().filter(meeting -> meeting.getName().equals(meetingName)).findFirst().orElse(null);
        if (foundMeeting == null)
            throw new ApiRequestException(HttpStatus.NOT_FOUND, "Meeting is not found");
        return new MeetingDto(
                foundMeeting.getName(),
                personService.getPersonByName(foundMeeting.getResponsiblePersonName()),
                foundMeeting.getDescription(),
                foundMeeting.getCategory(),
                foundMeeting.getType(),
                foundMeeting.getStartDate(),
                foundMeeting.getEndDate(),
                personService.getPeopleByNames(getParticipantsNamesByMeetingName(meetingName))
        );
    }


    public MeetingDto createNewMeeting(Meeting newMeeting) {
        List<Meeting> meetings = meetingRepository.getAll();

        if (meetings.stream().anyMatch(meeting -> meeting.getName().equals(newMeeting.getName())))
            throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Meeting name must be unique");
        if (personService.getPersonByName(newMeeting.getResponsiblePersonName()) == null)
            throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Provided person doesn't exist");

        meetings.add(newMeeting);
        meetingRepository.saveAll(meetings);
        addNewMeetingPersonRelationship(newMeeting.getName(), newMeeting.getResponsiblePersonName());

        return getMeetingByName(newMeeting.getName());
    }

    public MeetingDto deleteMeetingByName(String meetingName, String personName) {
        List<Meeting> meetings = meetingRepository.getAll();
        MeetingDto meetingToDelete = getMeetingByName(meetingName);

        if (!meetingToDelete.getResponsiblePerson().getName().equals(personName))
            throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Provided person is not the responsible person");

        meetings.removeIf(meeting -> meeting.getName().equals(meetingName));
        removeMeetingPersonRelationshipsByMeetingName(meetingName);

        meetingRepository.saveAll(meetings);

        return meetingToDelete;
    }

    public List<MeetingDto> getMeetings(String description,
                                        String responsiblePersonName,
                                        MeetingCategory meetingCategory,
                                        MeetingType meetingType,
                                        LocalDate startDate,
                                        LocalDate endDate,
                                        Integer numberOfParticipants) {
        List<MeetingDto> meetingDtos = getAllUnfilteredMeetings();

        Predicate<MeetingDto> filterCondition = meeting -> true;

        filterCondition = getNewFilterCondition(description, filterCondition, meeting ->
                meeting.getDescription().toLowerCase().contains(description.toLowerCase()));

        filterCondition = getNewFilterCondition(responsiblePersonName, filterCondition, meeting ->
                meeting.getResponsiblePerson().getName().toLowerCase().contains(responsiblePersonName.toLowerCase()));

        filterCondition = getNewFilterCondition(meetingCategory, filterCondition, meeting ->
                meeting.getCategory().equals(meetingCategory));

        filterCondition = getNewFilterCondition(meetingType, filterCondition, meeting ->
                meeting.getType().equals(meetingType));

        filterCondition = getNewFilterCondition(startDate, filterCondition, meeting -> {
            LocalDateTime meetingStartDate = meeting.getStartDate();
            return (meetingStartDate.isAfter(startDate.atStartOfDay())
                    && (endDate == null || meetingStartDate.isBefore(endDate.atStartOfDay())));
        });

        filterCondition = getNewFilterCondition(numberOfParticipants, filterCondition, meeting ->
                meeting.getParticipants().size() >= numberOfParticipants);

        return meetingDtos.stream()
                .filter(filterCondition)
                .toList();
    }

    private List<MeetingDto> getAllUnfilteredMeetings() {
        List<Meeting> meetings = meetingRepository.getAll();
        List<MeetingDto> outputMeetings = new ArrayList<>();
        meetings.forEach(meeting -> {
            MeetingDto meetingDto = getMeetingByName(meeting.getName());
            outputMeetings.add(meetingDto);
        });
        return outputMeetings;
    }

    private <T> Predicate<MeetingDto> getNewFilterCondition(
            T filterParam,
            Predicate<MeetingDto> currentCondition,
            Predicate<MeetingDto> conditionToAdd) {
        if (filterParam == null) return currentCondition;
        return currentCondition.and(conditionToAdd);
    }

    private List<String> getParticipantsNamesByMeetingName(String meetingName) {
        List<MeetingPerson> meetingsPeople = meetingRepository.getAllMeetingsPeopleRelationships();
        List<String> peopleNames = new ArrayList<>();
        meetingsPeople.forEach(meetingPerson -> {
            if (meetingPerson.getMeetingName().equals(meetingName)) {
                peopleNames.add(meetingPerson.getPersonName());
            }
        });
        return peopleNames;
    }

    public MeetingPersonDto addNewMeetingPersonRelationship(String meetingName, String personName) {
        List<MeetingPerson> meetingsPeople = meetingRepository.getAllMeetingsPeopleRelationships();

        if (getParticipantsNamesByMeetingName(meetingName).contains(personName))
            throw new ApiRequestException(HttpStatus.BAD_REQUEST, "This person is already in the meeting");
        if (getMeetingByName(meetingName) == null) // paklaust del situ kaip reiktu daryt..
            throw new ApiRequestException(HttpStatus.NOT_FOUND, "Provided meeting doesn't exist");
        if (personService.getPersonByName(personName) == null)
            throw new ApiRequestException(HttpStatus.NOT_FOUND, "Provided person doesn't exist"); // message is universal but the use cases are not..

        LocalDateTime currentTime = LocalDateTime.now();
        meetingsPeople.add(new MeetingPerson(meetingName, personName, currentTime));
        meetingRepository.saveAllMeetingsPeopleRelationships(meetingsPeople);

        return new MeetingPersonDto(personService.getPersonByName(personName), currentTime);
    }

    public MeetingPersonDto removeMeetingPersonRelationship(String meetingName, String personName) {
        List<MeetingPerson> meetingsPeople = meetingRepository.getAllMeetingsPeopleRelationships();

        if (getMeetingByName(meetingName).getResponsiblePerson().getName().equals(personName))
            throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Cannot delete the person responsible for the meeting");

        MeetingPerson personToRemoveFromMeeting = meetingsPeople.stream().filter(meetingPerson ->
                meetingPerson.getPersonName().equals(personName) && meetingPerson.getMeetingName().equals(meetingName)
        ).findFirst().orElse(null);

        if (personToRemoveFromMeeting == null)
            throw new ApiRequestException(HttpStatus.NOT_FOUND, "Person is not Found");

        MeetingPersonDto personToDeleteDto = new MeetingPersonDto(
                personService.getPersonByName(personName),
                personToRemoveFromMeeting.getDateCreated()
        );

        meetingsPeople.removeIf(meetingPerson -> Objects.deepEquals(meetingPerson, personToRemoveFromMeeting));
        meetingRepository.saveAllMeetingsPeopleRelationships(meetingsPeople);

        return personToDeleteDto;
    }

    private void removeMeetingPersonRelationshipsByMeetingName(String meetingName) {
        List<MeetingPerson> meetingsPeople = meetingRepository.getAllMeetingsPeopleRelationships();

        List<MeetingPerson> newMeetingsPeople = meetingsPeople.stream().filter(meetingPerson ->
                !meetingPerson.getMeetingName().equals(meetingName)).toList();

        meetingRepository.saveAllMeetingsPeopleRelationships(newMeetingsPeople);
    }
}
