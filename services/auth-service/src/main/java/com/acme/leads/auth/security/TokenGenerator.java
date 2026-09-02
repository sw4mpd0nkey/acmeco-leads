package com.acme.leads.auth.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.acme.leads.auth.model.User;
import com.acme.leads.auth.repository.UserRepository;
import com.acme.leads.shared.security.TokenUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenGenerator {
    private final UserRepository userRepository;
    private final TokenUtils tokenUtils;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.accessExpiration}")
    private Long accessExpiration;

    @Value("${token.refreshExpiration}")
    private Long refreshExpiration;

    public String refreshAccessToken(String refreshToken) {
        String username = tokenUtils.getUsername(refreshToken);
        if (username == null) {
            throw new AuthenticationException("Refresh token isn't valid!") {};
        }
        return generateAccessToken(username);
    }

    public String generateAccessToken(String username) {
        Map<String, Object> claims = generateClaims(username);
        return Jwts.builder()
                .setClaims(claims) // It has to be first because it overwrites the rest
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateRefreshToken(String username) {
        validateUser(username);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Map<String, Object> generateClaims(String username) {
        Map<String, Object> claims = new HashMap<>();

        User user = validateUser(username);
        Long userId = user.getId();
        claims.put("userId", userId);

        List<String> authorities =
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", authorities);


        return claims;
    }

    private User validateUser(String username) {
        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new NotFoundException("User not found"));
        if (user.isDeleted()) {
            throw new BadRequestException("User is deleted");
        }
        return user;
    }
}