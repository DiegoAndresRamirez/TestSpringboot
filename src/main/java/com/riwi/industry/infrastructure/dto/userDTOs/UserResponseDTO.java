package com.riwi.industry.infrastructure.dto.userDTOs;

import com.riwi.industry.infrastructure.dto.palletDTOs.PalletResponseDTO;
import com.riwi.industry.utils.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private PalletResponseDTO pallet_id;
}
