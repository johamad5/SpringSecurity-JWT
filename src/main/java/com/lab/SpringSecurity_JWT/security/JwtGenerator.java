package com.lab.SpringSecurity_JWT.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtGenerator {
    byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.JWT_SIGNATURE);
    SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_SIGNATURE.getBytes());

    public String generateToken(Authentication authentication){

        Date generationDate = new Date();
        Date expirationDate = new Date(generationDate.getTime() + SecurityConstants.JWT_EXPIRATION_TOKEN);

        return Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(generationDate)
                .expiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token){
        Claims claims = (Claims) Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean tokenValidator(String token){
        try{
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        }catch(Exception e){
            throw new AuthenticationCredentialsNotFoundException("Jwt invalid or expired");
        }
    }
}
