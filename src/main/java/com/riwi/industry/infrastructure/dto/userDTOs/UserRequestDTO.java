package com.riwi.industry.infrastructure.dto.userDTOs;

import com.riwi.industry.domain.models.Pallet;
import com.riwi.industry.utils.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    private String username;
    private String name;
    private String lastname;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private Long pallet_id;
}
