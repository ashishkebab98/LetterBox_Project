package com.letterbox.customer.customer_profile_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // For simplicity in API development, can be enabled later
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users", "/users/register").permitAll() // Allow registration without authentication
                        .anyRequest().authenticated())
                .httpBasic(httpBasic -> {}); // Basic HTTP authentication for now

        return http.build();
    }
}