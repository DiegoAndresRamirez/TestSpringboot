package com.riwi.industry.infrastructure.controllers;

import com.riwi.industry.application.services.AuthService;
import com.riwi.industry.infrastructure.dto.authDtos.LoginRequestDTO;
import com.riwi.industry.infrastructure.dto.authDtos.RegisterRequestDTO;
import com.riwi.industry.utils.enums.Role;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "Endpoints for user authentication")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Logs in a user and returns a token.")
    public ResponseEntity<?> login(
            @Parameter(description = "Login request payload", required = true)
            @RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        try {
            return ResponseEntity.status(200).body(authService.login(loginRequestDTO));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "User Registration", description = "Registers a new user.")
    public ResponseEntity<?> register(
            @Parameter(description = "Registration request payload", required = true)
            @RequestBody @Valid RegisterRequestDTO registerRequestDTO,
            @Parameter(description = "User role", required = true)
            @RequestParam Role role) {
        try {
            return ResponseEntity.status(201).body(authService.register(registerRequestDTO, role));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("User already exists");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Role not found");
        }
    }
}
