package com.devjean.gamelist.application.web.handlers;

import com.devjean.gamelist.application.web.commons.DuplicateTitleException;
import com.devjean.gamelist.application.web.commons.EntityNotFoundException;
import com.devjean.gamelist.application.web.commons.IllegalArgumentException;
import com.devjean.gamelist.application.web.controllers.ErrorField;
import com.devjean.gamelist.application.web.controllers.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Validation Error: {}", ex.getMessage());

        List<ErrorField> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorField(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse("Validation Error", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(MethodArgumentTypeMismatchException ex) {
        log.error("Bad Request: Invalid type for parameter '{}'. Expected type: {} but received value: '{}'.",
                ex.getName(), Objects.requireNonNull(ex.getRequiredType()).getName(), ex.getValue());

        ErrorResponse errorResponse = new ErrorResponse(
                "Bad Request",
                Collections.singletonList(new ErrorField("id", "Invalid type for parameter 'id': " + ex.getValue()))
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Entity Not Found: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "Entity Not Found",
                Collections.singletonList(new ErrorField("id", ex.getMessage()))
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal Argument Exception: {}", ex.getMessage());

        String field = ex.getMessage().contains("sourceIndex") ? "sourceIndex" : "destinationIndex";

        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid Index",
                Collections.singletonList(new ErrorField(field, ex.getMessage()))
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DuplicateTitleException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateTitleException(DuplicateTitleException ex) {
        log.error("Duplicate Title Exception: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "Duplicate title",
                Collections.singletonList(new ErrorField("title", ex.getMessage()))
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
