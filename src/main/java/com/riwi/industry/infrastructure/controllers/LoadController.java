package com.riwi.industry.infrastructure.controllers;

import com.riwi.industry.application.interfaces.ILoadService;
import com.riwi.industry.infrastructure.dto.loadDTOs.LoadRequestDTO;
import com.riwi.industry.infrastructure.dto.loadDTOs.LoadResponseDTO;
import com.riwi.industry.infrastructure.interfaces.ILoadController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loads")
@Tag(name = "Load Management API", description = "Endpoints for managing loads")
public class LoadController implements ILoadController {

    @Autowired
    private ILoadService loadService;

    @Override
    @PostMapping
    @Operation(summary = "Create Load", description = "Creates a new load.")
    public ResponseEntity<LoadResponseDTO> create(
            @Parameter(description = "Load request payload", required = true)
            @RequestBody LoadRequestDTO loadRequestDTO) {
        try {
            LoadResponseDTO loadResponseDTO = loadService.create(loadRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(loadResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    @PutMapping("/{id}")
    @Operation(summary = "Update Load", description = "Updates an existing load by ID.")
    public ResponseEntity<LoadResponseDTO> update(
            @Parameter(description = "Load request payload", required = true)
            @RequestBody LoadRequestDTO loadRequestDTO,
            @Parameter(description = "ID of the load to update", required = true)
            @PathVariable Long id) {
        try {
            LoadResponseDTO loadResponseDTO = loadService.update(loadRequestDTO, id);
            return ResponseEntity.status(HttpStatus.OK).body(loadResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Get Load by ID", description = "Retrieves a load by its ID.")
    public ResponseEntity<LoadResponseDTO> getById(
            @Parameter(description = "ID of the load to retrieve", required = true)
            @PathVariable Long id) {
        try {
            LoadResponseDTO loadResponseDTO = loadService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(loadResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    @GetMapping
    @Operation(summary = "Get All Loads", description = "Retrieves a list of all loads.")
    public ResponseEntity<List<LoadResponseDTO>> getAll() {
        try {
            List<LoadResponseDTO> loads = loadService.getAll();
            return ResponseEntity.ok(loads);
        } catch (NullPointerException e) {
            e.printStackTrace(); // Imprimir en consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir en consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Load", description = "Deletes a load by its ID.")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID of the load to delete", required = true)
            @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(loadService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
