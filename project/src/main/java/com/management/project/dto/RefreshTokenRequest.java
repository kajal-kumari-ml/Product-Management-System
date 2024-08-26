package com.management.project.dto;


public class RefreshTokenRequest {

    private String token;

    public RefreshTokenRequest() {
    }

    public RefreshTokenRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String toString() {
        return "RefreshTokenRequest(token=" + this.getToken() + ")";
    }
    
}
