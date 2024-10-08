package com.riwi.industry.application.services;

import com.riwi.industry.application.interfaces.IPalletService;
import com.riwi.industry.domain.models.Load;
import com.riwi.industry.domain.models.Pallet;
import com.riwi.industry.domain.models.User;
import com.riwi.industry.domain.persistence.LoadRepository;
import com.riwi.industry.domain.persistence.PalletRepository;
import com.riwi.industry.domain.persistence.UserRepository;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletLoadResponseDTO;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletRequestDTO;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletResponseDTO;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PalletService implements IPalletService {

    @Autowired
    private PalletRepository palletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoadRepository loadRepository;

    @Override
    public PalletResponseDTO create(PalletRequestDTO palletRequestDTO) {
        // Verificar si el pallet ya existe
        if (palletRepository.findByCode(palletRequestDTO.getCode()) != null) {
            throw new EntityExistsException("Pallet already exists");
        }

        // Verificar si el transportista existe
        User carrier = userRepository.findById(palletRequestDTO.getCarrier_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Cargar los loads asociados
        List<Load> loads = loadRepository.findAllById(palletRequestDTO.getLoads_ids());

        // Crear nuevo pallet
        Pallet newPallet = Pallet.builder()
                .code(palletRequestDTO.getCode())
                .ubication(palletRequestDTO.getUbication())
                .type(palletRequestDTO.getType())
                .maxCapacity(palletRequestDTO.getMaxCapacity())
                .carrier(carrier)
                .loads(loads)  // Usar la lista de loads existente
                .build();

        // Guardar el nuevo pallet
        palletRepository.save(newPallet);

        return buildPalletResponseDTO(newPallet);
    }

    @Override
    public PalletResponseDTO update(PalletRequestDTO palletRequestDTO, Long palletId) {
        // Verificar si el pallet existe
        Pallet pallet = palletRepository.findById(palletId)
                .orElseThrow(() -> new IllegalArgumentException("Pallet not found"));

        // Verificar si el transportista existe
        User carrier = userRepository.findById(palletRequestDTO.getCarrier_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Actualizar los campos del pallet
        pallet.setCode(palletRequestDTO.getCode());
        pallet.setUbication(palletRequestDTO.getUbication());
        pallet.setType(palletRequestDTO.getType());
        pallet.setMaxCapacity(palletRequestDTO.getMaxCapacity());
        pallet.setCarrier(carrier);
        pallet.setLoads(loadRepository.findAllById(palletRequestDTO.getLoads_ids()));

        // Guardar el pallet actualizado
        palletRepository.save(pallet);

        return buildPalletResponseDTO(pallet);
    }

    @Override
    public PalletResponseDTO getById(Long palletId) {
        Pallet pallet = palletRepository.findById(palletId)
                .orElseThrow(() -> new IllegalArgumentException("Pallet not found"));

        return buildPalletResponseDTO(pallet);
    }

    @Override
    public List<PalletResponseDTO> getAll() {
        List<Pallet> pallets = palletRepository.findAll();
        return pallets.stream().map(this::buildPalletResponseDTO).collect(Collectors.toList());
    }

    @Override
    public String delete(Long palletId) {
        Pallet pallet = palletRepository.findById(palletId)
                .orElseThrow(() -> new IllegalArgumentException("Pallet not found"));

        palletRepository.delete(pallet);
        return "Pallet deleted";
    }

    // Método auxiliar para construir el DTO de respuesta
    private PalletResponseDTO buildPalletResponseDTO(Pallet pallet) {
        return PalletResponseDTO.builder()
                .status(pallet.getStatus())
                .ubication(pallet.getUbication())
                .type(pallet.getType())
                .code(pallet.getCode())
                .maxCapacity(pallet.getMaxCapacity())
                .carrier_id(pallet.getCarrier() != null ? pallet.getCarrier().getId() : null)
                .loads_ids(pallet.getLoads().stream()
                        .map(load -> PalletLoadResponseDTO.builder()
                                .weight(load.getWeight())
                                .dimensions(load.getDimensions())
                                .status(load.getStatus())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
