package com.riwi.industry.application.services;

import com.riwi.industry.application.interfaces.IPalletService;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletRequestDTO;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PalletService implements IPalletService {
    @Override
    public PalletResponseDTO create(PalletRequestDTO palletRequestDTO) {
        return null;
    }

    @Override
    public PalletResponseDTO update(PalletRequestDTO palletRequestDTO, Long aLong) {
        return null;
    }

    @Override
    public PalletResponseDTO getById(Long aLong) {
        return null;
    }

    @Override
    public List<PalletResponseDTO> getAll() {
        return List.of();
    }

    @Override
    public String delete(Long aLong) {
        return "";
    }
}
