package com.riwi.industry.infrastructure.dto.loadDTOs;

import com.riwi.industry.infrastructure.dto.palletDTOs.PalletResponseDTO;
import com.riwi.industry.utils.enums.StatusLoad;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadResponseDTO {
    private Long id;
    private Long weight;
    private String dimensions;
    @Enumerated(value = EnumType.STRING)
    private StatusLoad status;
    private PalletResponseDTO palletId;
}
