package com.visma.meetingsmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Meeting {
    private Integer id;
    private String name;
    private Integer responsiblePersonId;
    private String description;
    private String category; // implement enum for this
    private String type; // implement enum for this
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
