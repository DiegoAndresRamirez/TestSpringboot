package com.riwi.industry.infrastructure.dto.authDtos;

import com.riwi.industry.utils.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.validation.constraints.Null;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDTO {
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private Long pallet_id;

}