package com.example.iottrackingsystem.exception;

public class RestTemplateCustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RestTemplateCustomException() {
        super();
    }

    public RestTemplateCustomException(String message) {
        super(message);
    }
}
