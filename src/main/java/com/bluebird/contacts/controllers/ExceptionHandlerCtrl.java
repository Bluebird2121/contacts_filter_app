package com.bluebird.contacts.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionHandlerCtrl {

    @ExceptionHandler({IllegalArgumentException.class})
    ResponseEntity handleIllegalArgumentException(IllegalArgumentException ex, HttpServletResponse response) throws IOException {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

}
