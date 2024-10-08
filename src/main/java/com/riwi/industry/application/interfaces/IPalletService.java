package com.riwi.industry.application.interfaces;

import com.riwi.industry.application.generic.IGenericServices;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletRequestDTO;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletResponseDTO;

public interface IPalletService extends IGenericServices<PalletRequestDTO, PalletResponseDTO, Long, String> {
}
