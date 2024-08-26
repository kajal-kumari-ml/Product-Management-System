package com.management.project.dto;


public class JwtResponse {

    private String token;
    private String refreshToken;

    public JwtResponse() {
    }

    public JwtResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String toString() {
        return "JwtResponse(token=" + this.getToken() + ", refreshToken=" + this.getRefreshToken() + ")";
    }
    
    
}