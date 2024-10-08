package com.riwi.industry.infrastructure.controllers;

import com.riwi.industry.application.interfaces.IPalletService;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletRequestDTO;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletResponseDTO;
import com.riwi.industry.infrastructure.interfaces.IPalletController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pallets")
@Tag(name = "Pallet Management API", description = "Endpoints for managing pallets")
public class PalletController implements IPalletController {

    @Autowired
    private IPalletService palletService;

    @Override
    @PostMapping
    @Operation(summary = "Create Pallet", description = "Creates a new pallet.")
    public ResponseEntity<PalletResponseDTO> create(
            @Parameter(description = "Pallet request payload", required = true)
            @RequestBody PalletRequestDTO palletRequestDTO) {
        try {
            PalletResponseDTO palletResponseDTO = palletService.create(palletRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(palletResponseDTO);
        } catch (Exception e) {
            e.printStackTrace(); // Registro del error para facilitar el diagnóstico
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    @PutMapping("/{id}")
    @Operation(summary = "Update Pallet", description = "Updates an existing pallet by ID.")
    public ResponseEntity<PalletResponseDTO> update(
            @Parameter(description = "Pallet request payload", required = true)
            @RequestBody PalletRequestDTO palletRequestDTO,
            @Parameter(description = "ID of the pallet to update", required = true)
            @PathVariable("id") Long id) {
        try {
            PalletResponseDTO palletResponseDTO = palletService.update(palletRequestDTO, id);
            return ResponseEntity.status(HttpStatus.OK).body(palletResponseDTO);
        } catch (Exception e) {
            e.printStackTrace(); // Registro del error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Get Pallet by ID", description = "Retrieves a pallet by its ID.")
    public ResponseEntity<PalletResponseDTO> getById(
            @Parameter(description = "ID of the pallet to retrieve", required = true)
            @PathVariable("id") Long id) {
        try {
            PalletResponseDTO palletResponseDTO = palletService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(palletResponseDTO);
        } catch (Exception e) {
            e.printStackTrace(); // Registro del error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    @GetMapping
    @Operation(summary = "Get All Pallets", description = "Retrieves a list of all pallets.")
    public ResponseEntity<List<PalletResponseDTO>> getAll() {
        try {
            List<PalletResponseDTO> pallets = palletService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(pallets);
        } catch (Exception e) {
            e.printStackTrace(); // Registro del error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Pallet", description = "Deletes a pallet by its ID.")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID of the pallet to delete", required = true)
            @PathVariable("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(palletService.delete(id));
        } catch (Exception e) {
            e.printStackTrace(); // Registro del error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
