package com.management.project.exception;

public class BadRequest extends Exception {

    private static final long serialVersionUID = 1L;

    public BadRequest() {
        super();
    }
    public BadRequest(String message) {
        super(message);
    }
    
}
