package com.riwi.industry.infrastructure.controllers;

import com.riwi.industry.application.interfaces.IUserService;
import com.riwi.industry.infrastructure.dto.userDTOs.UserRequestDTO;
import com.riwi.industry.infrastructure.dto.userDTOs.UserResponseDTO;
import com.riwi.industry.infrastructure.interfaces.IUserController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management API", description = "Endpoints for managing users")
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;

    @Override
    @PostMapping("/create")
    @Operation(summary = "Create User", description = "Creates a new user.")
    public ResponseEntity<UserResponseDTO> create(
            @Parameter(description = "User request payload", required = true)
            @RequestBody UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO userResponseDTO = userService.create(userRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    @PutMapping("/{id}")
    @Operation(summary = "Update User", description = "Updates an existing user by ID.")
    public ResponseEntity<UserResponseDTO> update(
            @Parameter(description = "User request payload", required = true)
            @RequestBody UserRequestDTO userRequestDTO,
            @Parameter(description = "ID of the user to update", required = true)
            @PathVariable("id") Long aLong) {
        try {
            UserResponseDTO userResponseDTO = userService.update(userRequestDTO, aLong);
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Get User by ID", description = "Retrieves a user by its ID.")
    public ResponseEntity<UserResponseDTO> getById(
            @Parameter(description = "ID of the user to retrieve", required = true)
            @PathVariable("id") Long aLong) {
        try {
            UserResponseDTO userResponseDTO = userService.getById(aLong);
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    @GetMapping("/all")
    @Operation(summary = "Get All Users", description = "Retrieves a list of all users.")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User", description = "Deletes a user by its ID.")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID of the user to delete", required = true)
            @PathVariable("id") Long aLong) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.delete(aLong));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
