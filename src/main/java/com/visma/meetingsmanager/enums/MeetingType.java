package com.visma.meetingsmanager.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum MeetingType {
    LIVE("Live"),
    IN_PERSON("InPerson");

    @Getter
    @JsonValue
    private final String value;

    @Override
    public String toString() {
        return value;
    }

    //    public static MeetingType valueOfLabel(String label) {
//        for (MeetingType e : values()) {
//            if (e.value.equals(label)) {
//                return e;
//            }
//        }
//        return null;
//    }
    private static final Map<String, @NotNull MeetingType> BY_VALUE = new HashMap<>();

    static {
        for (MeetingType e : values()) {
            BY_VALUE.put(e.value, e);
        }
    }

    public static MeetingType valueOfLabel(String label) {
        return BY_VALUE.get(label);
    }
}
