package com.devjean.gamelist.application.web.handlers;

import com.devjean.gamelist.application.web.commons.EntityNotFoundException;
import com.devjean.gamelist.application.web.controllers.ErrorField;
import com.devjean.gamelist.application.web.controllers.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Collections;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

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

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("No Handler Found: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid Path",
                Collections.singletonList(new ErrorField("path", "The path '" + ex.getRequestURL() + "' is not valid."))
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
