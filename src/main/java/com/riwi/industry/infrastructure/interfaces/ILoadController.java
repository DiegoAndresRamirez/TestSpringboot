package com.riwi.industry.infrastructure.interfaces;

import com.riwi.industry.infrastructure.dto.loadDTOs.LoadRequestDTO;
import com.riwi.industry.infrastructure.dto.loadDTOs.LoadResponseDTO;
import com.riwi.industry.infrastructure.generic.IGenericControllers;

public interface ILoadController extends IGenericControllers<LoadRequestDTO, LoadResponseDTO, Long> {
}
