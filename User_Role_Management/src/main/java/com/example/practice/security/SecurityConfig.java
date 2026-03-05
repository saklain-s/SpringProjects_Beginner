package com.example.practice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // 1. Order matters: Specific to General
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll() // Allow public access
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // Requires ROLE_ADMIN authority
                        .anyRequest().authenticated() // Everything else needs a login
                )
                // 2. Use the Customizer for httpBasic
                .httpBasic(org.springframework.security.config.Customizer.withDefaults())
                // 3. Recommended for APIs to prevent session fixation issues
                .sessionManagement(session -> session
                        .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
