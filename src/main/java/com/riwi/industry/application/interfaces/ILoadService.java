package com.riwi.industry.application.interfaces;

import com.riwi.industry.application.generic.IGenericCrud;
import com.riwi.industry.infrastructure.dto.loadDTOs.LoadRequestDTO;
import com.riwi.industry.infrastructure.dto.loadDTOs.LoadResponseDTO;

public interface ILoadService extends IGenericCrud<LoadRequestDTO, LoadResponseDTO, Long, String> {
}
