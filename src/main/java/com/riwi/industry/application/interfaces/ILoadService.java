package com.riwi.industry.application.interfaces;

import com.riwi.industry.application.generic.IGenericServices;
import com.riwi.industry.infrastructure.dto.loadDTOs.LoadRequestDTO;
import com.riwi.industry.infrastructure.dto.loadDTOs.LoadResponseDTO;

public interface ILoadService extends IGenericServices<LoadRequestDTO, LoadResponseDTO, Long, String> {
}
