package com.example.TeamBinary_Backend.Config;

import com.example.TeamBinary_Backend.Security.JwtRequestFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtRequestFilter jwtRequestFilter;


    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {

        this.jwtRequestFilter = jwtRequestFilter;

    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http

                // =================================================
                // CSRF
                // =================================================

                .csrf(csrf -> csrf.disable())

                // =================================================
                // CORS
                // =================================================

                .cors(cors -> {})

                // =================================================
                // STATELESS
                // =================================================
                .sessionManagement(
                        session -> session.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS
                                )
                )


                // =================================================
                // AUTHORIZATION
                // =================================================

                .authorizeHttpRequests(auth -> auth


                                // ---------------------------------
                                // CORS PREFLIGHT
                                // ---------------------------------

                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()


                                // ---------------------------------
                                // LOGIN
                                // ---------------------------------

                                .requestMatchers("/api/auth/**").permitAll()


                                // ---------------------------------
                                // SYSTEM ADMIN
                                // ---------------------------------

                                .requestMatchers("/api/organizations/**").hasRole("SYSTEM_ADMIN")


                                // ---------------------------------
                                // ADMIN
                                // ---------------------------------

                                .requestMatchers("/api/cameras/**").hasRole("ADMIN")
                                .requestMatchers("/api/networks/**").hasRole("ADMIN")
                                .requestMatchers("/api/investigators/**").hasRole("ADMIN")
                                .requestMatchers("/api/settings/**").hasRole("ADMIN")


                                // ---------------------------------
                                // ADMIN + INVESTIGATOR
                                // ---------------------------------

                                .requestMatchers("/api/number-plate/**").hasAnyRole("ADMIN", "INVESTIGATOR")
                                .requestMatchers("/api/vehicle/**").hasAnyRole("ADMIN", "INVESTIGATOR")
                                .requestMatchers("/api/vehicle-search/**").hasAnyRole("ADMIN", "INVESTIGATOR")
                                .requestMatchers("/api/reports/**").hasAnyRole("ADMIN", "INVESTIGATOR")


                                // ---------------------------------
                                // EVERYTHING ELSE
                                // ---------------------------------

                                .anyRequest()
                                .authenticated()
                );


        // =========================================================
        // JWT FILTER
        // =========================================================

        http.addFilterBefore(
                jwtRequestFilter,
                UsernamePasswordAuthenticationFilter.class
        );


        return http.build();
    }
}