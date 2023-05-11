package com.visma.meetingsmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Person {
    private Integer id;
    private String name;
    private Integer[] participatingInMeetingIds;
}
