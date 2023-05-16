package com.visma.meetingsmanager.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.visma.meetingsmanager.exceptions.ApiRequestException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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

    private static final Map<String, @NotNull MeetingType> BY_VALUE = new HashMap<>();

    static {
        for (MeetingType e : values()) {
            BY_VALUE.put(e.value, e);
        }
    }

    public static MeetingType valueOfLabel(String label) {
        if (BY_VALUE.get(label) == null && label != null)
            throw new ApiRequestException(
                    HttpStatus.BAD_REQUEST,
                    "invalid value '" + label + "', should be of type 'Live' or 'InPerson'"
            );
        return BY_VALUE.get(label);
    }
}
