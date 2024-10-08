package com.riwi.industry.configuration.security;

import com.riwi.industry.utils.helpers.JWTFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@Component
@Configuration
public class SecurityConfig {

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    JWTFilter jwtFilter;

    String[] PUBLIC_URLS = {"api/auth/login", "api/auth/register", "/swagger-ui/**", "v3/api-docs/**", "/v3/api-docs.yaml",
            "/swagger-ui.html", "/webjars/**", "/swagger-resources/**"};

    String[] ADMIN_URLS = {"api/pallets", "api/pallets/{id}", "api/loads", "api/loads/{id}", "/api/loads/{id}/status", "/api/loads/{id}/damage", "/api/pallets/{id}/loads", "/api/audit-logs"};

    String[] CARRIER_URLS = {"/api/loads/{id}/status", "/api/loads/{id}/damage", "/api/carriers/loads", "/api/pallets/{id}/loads"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .requestMatchers(ADMIN_URLS).hasAuthority("ADMIN")
                        .requestMatchers(CARRIER_URLS).hasAuthority("CARRIER")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }
}
