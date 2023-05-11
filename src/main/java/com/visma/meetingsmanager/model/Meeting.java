package com.visma.meetingsmanager.model;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Indexed;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Meeting {
    @JsonTypeId
    private Integer id;
    private String name;
    private Integer responsiblePersonId;
    private String description;
    private String category;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
