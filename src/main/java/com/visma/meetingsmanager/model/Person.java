package com.visma.meetingsmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Person {
    private Integer id;
    private String name;
    private List<Integer> participatingInMeetingIds;
}
