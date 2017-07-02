package com.bluebird.contacts.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;

import com.bluebird.contacts.dtos.ErrorDto;

@ControllerAdvice
public class ExceptionHandlerCtrl {

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    ResponseEntity<ErrorDto> handleException(Exception ex) throws IOException {
        ErrorDto errorDto = new ErrorDto(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

}
