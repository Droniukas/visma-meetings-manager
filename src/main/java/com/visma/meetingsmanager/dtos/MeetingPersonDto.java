package com.visma.meetingsmanager.dtos;

import com.visma.meetingsmanager.models.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MeetingPersonDto {
    private Person person;
    private LocalDateTime dateCreated;
}
