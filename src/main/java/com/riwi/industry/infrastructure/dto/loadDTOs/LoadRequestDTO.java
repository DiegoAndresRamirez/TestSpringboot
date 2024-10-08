package com.riwi.industry.infrastructure.dto.loadDTOs;

import com.riwi.industry.utils.enums.StatusLoad;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadRequestDTO {
    private Long weight;
    private String code;
    private String dimensions;
    @Enumerated(value = EnumType.STRING)
    private StatusLoad status;
    private Long palletId;
}
