package com.riwi.industry.infrastructure.dto.authDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO {

    @NotBlank(message = "required email")
    private String email;
    @NotBlank(message = "required password")
    private String password;
}
