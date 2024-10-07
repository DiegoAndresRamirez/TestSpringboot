package com.riwi.industry.application.services;

import com.riwi.industry.application.interfaces.ILoadService;
import com.riwi.industry.infrastructure.dto.loadDTOs.LoadRequestDTO;
import com.riwi.industry.infrastructure.dto.loadDTOs.LoadResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadService implements ILoadService {
    @Override
    public LoadResponseDTO create(LoadRequestDTO loadRequestDTO) {
        return null;
    }

    @Override
    public LoadResponseDTO update(LoadRequestDTO loadRequestDTO, Long aLong) {
        return null;
    }

    @Override
    public LoadResponseDTO getById(Long aLong) {
        return null;
    }

    @Override
    public List<LoadResponseDTO> getAll() {
        return List.of();
    }

    @Override
    public String delete(Long aLong) {
        return "";
    }
}
