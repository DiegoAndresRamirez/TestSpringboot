package com.riwi.industry.infrastructure.dto.palletDTOs;

import com.riwi.industry.utils.enums.StatusLoad;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PalletLoadResponseDTO {

    private Long id;
    private Long weight;
    private String dimensions;
    @Enumerated(value = EnumType.STRING)
    private StatusLoad status;
}
