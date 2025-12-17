package com.OpenHearing.Usermanagement.dto;

public class LoginRequest {
    private String email;
    private String password;

    // Default Constructor
    public LoginRequest() {}

    // Constructor with fields
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // --- MANUAL GETTERS AND SETTERS (Fixes the error) ---

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}