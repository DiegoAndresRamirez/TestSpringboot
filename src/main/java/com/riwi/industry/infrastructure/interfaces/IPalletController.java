package com.riwi.industry.infrastructure.interfaces;

import com.riwi.industry.infrastructure.dto.palletDTOs.PalletRequestDTO;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletResponseDTO;
import com.riwi.industry.infrastructure.generic.IGenericControllers;

public interface IPalletController extends IGenericControllers<PalletRequestDTO, PalletResponseDTO, Long> {
}
