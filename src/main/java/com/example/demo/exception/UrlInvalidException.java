package com.example.demo.exception;

public class UrlInvalidException extends RuntimeException {
    public UrlInvalidException(String message) {
        super(message);
    }
}
