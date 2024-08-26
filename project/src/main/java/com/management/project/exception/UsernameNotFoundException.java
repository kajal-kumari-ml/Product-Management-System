package com.management.project.exception;


public class UsernameNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException() {
        super();
    }
    public UsernameNotFoundException(String message) {
        super(message);
    }
    
}