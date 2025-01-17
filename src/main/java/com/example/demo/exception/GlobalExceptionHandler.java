package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UrlNullException.class})
    public ResponseEntity<String> handlerUrlNullException(UrlNullException e) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
    }

    @ExceptionHandler({UrlInvalidException.class})
    public ResponseEntity<String> handlerUrlInvalidException(UrlInvalidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}

