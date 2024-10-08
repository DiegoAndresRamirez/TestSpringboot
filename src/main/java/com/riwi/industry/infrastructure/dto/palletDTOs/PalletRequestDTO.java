package com.riwi.industry.infrastructure.dto.palletDTOs;

import com.riwi.industry.domain.models.Load;
import com.riwi.industry.domain.models.User;
import com.riwi.industry.utils.enums.StatusPallet;
import com.riwi.industry.utils.enums.TypePallet;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PalletRequestDTO {

    @Enumerated(value = EnumType.STRING)
    private StatusPallet status;

    private String ubication;

    private String code;

    @Enumerated(value = EnumType.STRING)
    private TypePallet type;

    private Long maxCapacity;

    private Long carrier_id;

    private List<Long> loads_ids;
}
