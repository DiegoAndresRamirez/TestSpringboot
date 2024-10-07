package com.riwi.industry.application.interfaces;

import com.riwi.industry.application.generic.IGenericCrud;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletRequestDTO;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletResponseDTO;

public interface IPalletService extends IGenericCrud<PalletRequestDTO, PalletResponseDTO, Long, String> {
}
