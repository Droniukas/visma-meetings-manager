package com.visma.meetingsmanager.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MeetingPerson {
    private String meetingName;
    private String personName;
    private LocalDateTime dateAdded;
}
