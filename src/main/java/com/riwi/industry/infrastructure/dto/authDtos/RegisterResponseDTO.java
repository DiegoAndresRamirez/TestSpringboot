package com.riwi.industry.infrastructure.dto.authDtos;


import com.riwi.industry.utils.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponseDTO {
    private String message;

    private Role role;
}