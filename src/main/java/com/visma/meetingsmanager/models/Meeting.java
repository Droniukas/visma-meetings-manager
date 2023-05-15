package com.visma.meetingsmanager.models;

import com.visma.meetingsmanager.enums.MeetingCategory;
import com.visma.meetingsmanager.enums.MeetingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String name;
    @NotBlank
    private String responsiblePersonName;
    @NotNull
    private String description;
    @NotNull
    private MeetingCategory category;
    @NotNull
    private MeetingType type;
    @NotNull
    //    make sure to properly validate dates and exception handling for them
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
}
