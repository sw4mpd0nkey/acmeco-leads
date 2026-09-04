package com.acme.leads.shared.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtHelper {


    @Value("${token.secret}")
    private String secret;

    public Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).build().parseSignedClaims(token).getBody();
        } catch (Exception exception) {
            return null;
        }
    }

    public boolean isExpired(String token) {
        try {
            return getClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
        } catch (Exception exception) {
            return true;
        }
    }

    public String getUsername(String token) {
        try {
            return getClaims(token).getSubject();
        } catch (Exception exception) {
            return null;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }
}