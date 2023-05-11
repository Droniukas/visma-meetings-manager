package com.visma.meetingsmanager.dto;

import com.visma.meetingsmanager.model.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MeetingDto {
    private Integer id;
    private String name;
    private Person ResponsiblePerson;
    private String description;
    private String category; // implement enum for this
    private String type; // implement enum for this
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Person[] participants;
}
