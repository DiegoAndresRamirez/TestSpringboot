package com.riwi.industry.infrastructure.dto.palletDTOs;

import com.riwi.industry.infrastructure.dto.loadDTOs.LoadResponseDTO;
import com.riwi.industry.utils.enums.StatusPallet;
import com.riwi.industry.utils.enums.TypePallet;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PalletResponseDTO {

    @Enumerated(value = EnumType.STRING)
    private StatusPallet status;

    private String ubication;

    @Enumerated(value = EnumType.STRING)
    private TypePallet type;

    private Long maxCapacity;

    private Long carrier_id;

    private List<PalletLoadResponseDTO> loads_ids;
}
