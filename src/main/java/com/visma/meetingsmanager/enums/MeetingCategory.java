package com.visma.meetingsmanager.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum MeetingCategory {


    CODE_MONKEY("CodeMonkey"),
    HUB("Hub"),
    SHORT("Short"),
    TEAM_BUILDING("TeamBuilding");

    @Getter
    @JsonValue
    private final String value;

    @Override
    public String toString() {
        return value;
    }

    private static final Map<String, MeetingCategory> BY_VALUE = new HashMap<>();

    static {
        for (MeetingCategory e : values()) {
            BY_VALUE.put(e.value, e);
        }
    }

    public static MeetingCategory valueOfLabel(String label) {
        return BY_VALUE.get(label);
    }
}
