package com.visma.meetingsmanager.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class ApiRequestException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
}
