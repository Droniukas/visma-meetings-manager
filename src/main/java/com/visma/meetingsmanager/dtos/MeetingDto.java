package com.visma.meetingsmanager.dtos;

import com.visma.meetingsmanager.enums.MeetingCategory;
import com.visma.meetingsmanager.enums.MeetingType;
import com.visma.meetingsmanager.models.Person;
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
    private String name;
    private Person ResponsiblePerson;
    private String description;
    private MeetingCategory category;
    private MeetingType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Person> participants;
}
