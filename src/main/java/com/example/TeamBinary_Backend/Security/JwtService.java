package com.example.TeamBinary_Backend.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;


    private final long jwtExpiration =
            24 * 60 * 60 * 1000L;


    // =========================================================
    // GENERATE TOKEN
    // =========================================================

    public String generateToken(
            String email,
            String role,
            String organizationId,
            String organizationCode
    ) {

        Map<String, Object> claims =
                new HashMap<>();


        claims.put(
                "role",
                role
        );


        claims.put(
                "organizationId",
                organizationId
        );


        claims.put(
                "organizationCode",
                organizationCode
        );


        return Jwts.builder()

                .claims(claims)

                .subject(email)

                .issuedAt(
                        new Date()
                )

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + jwtExpiration
                        )
                )

                .signWith(
                        getSigningKey()
                )

                .compact();
    }


    // =========================================================
    // USERNAME
    // =========================================================

    public String extractUsername(
            String token
    ) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }


    // =========================================================
    // ORGANIZATION ID
    // =========================================================

    public String extractOrganizationId(
            String token
    ) {

        return extractAllClaims(token)
                .get(
                        "organizationId",
                        String.class
                );
    }


    // =========================================================
    // ORGANIZATION CODE
    // =========================================================

    public String extractOrganizationCode(
            String token
    ) {

        return extractAllClaims(token)
                .get(
                        "organizationCode",
                        String.class
                );
    }


    // =========================================================
    // ROLE
    // =========================================================

    public String extractRole(
            String token
    ) {

        return extractAllClaims(token)
                .get(
                        "role",
                        String.class
                );
    }


    // =========================================================
    // CLAIM
    // =========================================================

    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver
    ) {

        Claims claims =
                extractAllClaims(token);

        return resolver.apply(
                claims
        );
    }


    // =========================================================
    // ALL CLAIMS
    // =========================================================

    private Claims extractAllClaims(
            String token
    ) {

        return Jwts.parser()

                .verifyWith(
                        getSigningKey()
                )

                .build()

                .parseSignedClaims(
                        token
                )

                .getPayload();
    }


    // =========================================================
    // TOKEN VALIDATION
    // =========================================================

    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {

        try {

            String username =
                    extractUsername(token);


            return username.equals(
                    userDetails.getUsername()
            )
                    &&
                    !isTokenExpired(token);

        } catch (Exception e) {

            return false;
        }
    }


    // =========================================================
    // EXPIRATION
    // =========================================================

    private boolean isTokenExpired(
            String token
    ) {

        return extractExpiration(
                token
        ).before(
                new Date()
        );
    }


    private Date extractExpiration(
            String token
    ) {

        return extractClaim(
                token,
                Claims::getExpiration
        );
    }


    // =========================================================
    // SIGNING KEY
    // =========================================================

    private SecretKey getSigningKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(
                        secret
                );


        return Keys.hmacShaKeyFor(
                keyBytes
        );
    }
}