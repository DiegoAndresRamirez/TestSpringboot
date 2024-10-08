package com.riwi.industry.infrastructure.interfaces;

import com.riwi.industry.infrastructure.dto.userDTOs.UserRequestDTO;
import com.riwi.industry.infrastructure.dto.userDTOs.UserResponseDTO;
import com.riwi.industry.infrastructure.generic.IGenericControllers;

public interface IUserController extends IGenericControllers<UserRequestDTO, UserResponseDTO, Long> {
}
