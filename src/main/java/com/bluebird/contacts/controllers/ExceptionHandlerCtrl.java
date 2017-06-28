package com.bluebird.contacts.controllers;

import com.bluebird.contacts.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionHandlerCtrl {

    @ExceptionHandler({IllegalArgumentException.class})
    ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletResponse response) throws IOException {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

}
