package com.riwi.industry.application.interfaces;

import com.riwi.industry.application.generic.IGenericServices;
import com.riwi.industry.infrastructure.dto.userDTOs.UserRequestDTO;
import com.riwi.industry.infrastructure.dto.userDTOs.UserResponseDTO;

public interface IUserService extends IGenericServices<UserRequestDTO, UserResponseDTO, Long, String> {
}
