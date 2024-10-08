package com.riwi.industry.infrastructure.dto.authDtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String message;

    private String token;
}