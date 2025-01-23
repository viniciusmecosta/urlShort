package com.example.demo.exception;

public class UrlNullException extends RuntimeException{
    public UrlNullException(String message) {
        super(message);
    }
}