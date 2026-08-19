package com.example.TeamBinary_Backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth

//                        // Allow browser CORS preflight
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Authentication endpoints
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/vehicle/**").permitAll()
                        .requestMatchers("/api/number-plate/**").permitAll()
                        .requestMatchers("/api/vehicle-search/**").permitAll()
                        .requestMatchers("/api/cameras/**").permitAll()

                        // Network endpoints
                        .requestMatchers("/api/networks/**").permitAll()

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}