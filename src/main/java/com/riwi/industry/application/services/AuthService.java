package com.riwi.industry.application.services;


import com.riwi.industry.domain.models.Pallet;
import com.riwi.industry.domain.models.User;
import com.riwi.industry.domain.persistence.PalletRepository;
import com.riwi.industry.domain.persistence.UserRepository;
import com.riwi.industry.infrastructure.dto.authDtos.LoginRequestDTO;
import com.riwi.industry.infrastructure.dto.authDtos.LoginResponseDTO;
import com.riwi.industry.infrastructure.dto.authDtos.RegisterRequestDTO;
import com.riwi.industry.infrastructure.dto.authDtos.RegisterResponseDTO;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletLoadResponseDTO;
import com.riwi.industry.utils.enums.Role;
import com.riwi.industry.utils.helpers.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PalletRepository palletRepository;

    @Autowired(required = false)
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTService jwtService;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail());

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return LoginResponseDTO.builder()
                .message("Login successful")
                .token(jwtService.generateToken(user))
                .build();
    }

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO, Role role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        System.out.println("Is authenticated: " + (authentication != null && authentication.isAuthenticated()));
        if (authentication != null) {
            System.out.println("Authorities: " + authentication.getAuthorities());
        }
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();

        if (isAuthenticated && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.CARRIER.name()))) {
            throw new IllegalArgumentException("Logged in users cannot register new accounts");
        }


        Pallet pallet = null;
        if (!Role.ADMIN.equals(role)) {
            if (registerRequestDTO.getPallet_id() == null) {
                throw new IllegalArgumentException("Pallet ID es requerido");
            }
            pallet = palletRepository.findById(registerRequestDTO.getPallet_id()).orElseThrow(() -> new IllegalArgumentException("Pallet not found"));
        }

        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .name(registerRequestDTO.getName())
                .lastname(registerRequestDTO.getLastname())
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .role(role)
                .pallet(pallet)
                .build();

        userRepository.save(user);

        return RegisterResponseDTO.builder()
                .message("User registered successfully")
                .role(role)
                .build();
    }
}
