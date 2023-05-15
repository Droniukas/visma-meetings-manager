package com.visma.meetingsmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler({ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                e.getHttpStatus(),
                ZonedDateTime.now(), null);
        return new ResponseEntity<>(apiException, e.getHttpStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiException> handleAllExceptions(Exception e) {
        HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(
                e.getLocalizedMessage(),
                badRequest,
                ZonedDateTime.now(),
                null);
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<ApiException> handleInvalidRequestParameterNamesExceptions(Exception e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getLocalizedMessage(),
                badRequest,
                ZonedDateTime.now(),
                null);
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,})
    public ResponseEntity<ApiException> handleValidationExceptions(
            MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                "Invalid request data",
                badRequest,
                ZonedDateTime.now(),
                errors);
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ApiException> handleJsonParseExceptions(HttpMessageNotReadableException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                "Invalid request data (parsing exception)",
                badRequest,
                ZonedDateTime.now(),
                null);
        return new ResponseEntity<>(apiException, badRequest);
    }
}
