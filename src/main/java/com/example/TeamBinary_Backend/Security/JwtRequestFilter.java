package com.example.TeamBinary_Backend.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter
        extends OncePerRequestFilter {


    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;


    public JwtRequestFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService
    ) {

        this.jwtService =
                jwtService;

        this.userDetailsService =
                userDetailsService;
    }


    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    ) throws ServletException, IOException {


        // =====================================================
        // GET AUTHORIZATION HEADER
        // =====================================================

        final String authorizationHeader =
                request.getHeader(
                        "Authorization"
                );


        String jwt = null;

        String email = null;


        // =====================================================
        // CHECK BEARER TOKEN
        // =====================================================

        if (

                authorizationHeader != null

                        &&

                        authorizationHeader.startsWith(
                                "Bearer "
                        )

        ) {

            jwt =
                    authorizationHeader.substring(
                            7
                    );


            try {

                email =
                        jwtService.extractUsername(
                                jwt
                        );

            } catch (Exception e) {

                System.out.println(
                        "Invalid JWT token: "
                                + e.getMessage()
                );
            }
        }


        // =====================================================
        // AUTHENTICATE USER
        // =====================================================

        if (

                email != null

                        &&

                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                == null

        ) {

            try {

                // ---------------------------------------------
                // Extract organization from token
                // ---------------------------------------------

                String organizationId =
                        jwtService.extractOrganizationId(
                                jwt
                        );


                if (

                        organizationId == null

                                ||

                                organizationId.isBlank()

                ) {

                    throw new RuntimeException(
                            "Organization ID missing from JWT"
                    );
                }


                // ---------------------------------------------
                // Load user using:
                //
                // EMAIL + ORGANIZATION
                // ---------------------------------------------

                UserPrincipal userDetails =
                        userDetailsService
                                .loadUserByEmailAndOrganization(
                                        email,
                                        organizationId
                                );


                // ---------------------------------------------
                // Validate token
                // ---------------------------------------------

                if (
                        jwtService.isTokenValid(
                                jwt,
                                userDetails
                        )
                ) {

                    UsernamePasswordAuthenticationToken authentication =

                            new UsernamePasswordAuthenticationToken(

                                    userDetails,

                                    null,

                                    userDetails.getAuthorities()

                            );


                    authentication.setDetails(

                            new WebAuthenticationDetailsSource()
                                    .buildDetails(
                                            request
                                    )

                    );


                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authentication
                            );
                }


            } catch (Exception e) {

                System.out.println(
                        "JWT authentication failed: "
                                + e.getMessage()
                );
            }
        }


        filterChain.doFilter(
                request,
                response
        );
    }
}