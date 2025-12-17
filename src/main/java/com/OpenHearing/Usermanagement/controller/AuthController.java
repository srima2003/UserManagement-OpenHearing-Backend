package com.OpenHearing.Usermanagement.controller;

import com.OpenHearing.Usermanagement.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        Map<String, String> response = new HashMap<>();
        
        // Simple hardcoded check for demo purposes
        // In real code, check database using UserRepository
        if ("admin@test.com".equals(loginRequest.getEmail()) && "admin123".equals(loginRequest.getPassword())) {
            response.put("message", "Login Successful");
            response.put("token", "dummy-jwt-token-12345");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid Credentials");
            return ResponseEntity.status(401).body(response);
        }
    }
}