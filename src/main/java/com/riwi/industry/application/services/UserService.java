package com.riwi.industry.application.services;

import com.riwi.industry.application.interfaces.IUserService;
import com.riwi.industry.infrastructure.dto.userDTOs.UserRequestDTO;
import com.riwi.industry.infrastructure.dto.userDTOs.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Override
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        return null;
    }

    @Override
    public UserResponseDTO update(UserRequestDTO userRequestDTO, Long aLong) {
        return null;
    }

    @Override
    public UserResponseDTO getById(Long aLong) {
        return null;
    }

    @Override
    public List<UserResponseDTO> getAll() {
        return List.of();
    }

    @Override
    public String delete(Long aLong) {
        return "";
    }
}
