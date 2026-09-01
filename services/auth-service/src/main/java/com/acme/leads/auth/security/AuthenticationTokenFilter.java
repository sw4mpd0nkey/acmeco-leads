package com.acme.leads.auth.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.acme.leads.auth.client.AuthFeignClient;
import static com.acme.leads.auth.security.SecurityUtils.BEARER_PREFIX;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    private final AuthFeignClient authFeignClient;
    private final TokenUtils tokenUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        String token = extractJwtFromRequest(request);
        String username = tokenUtils.getUsername(token);
        if ((username != null && SecurityContextHolder.getContext().getAuthentication() == null)) {
            UserDetails userDetails = getUserDetails(username);
            if (tokenUtils.validateToken(token, userDetails)) {
                Claims claims = tokenUtils.getClaims(token);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, claims, userDetails.getAuthorities());
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    protected UserDetails getUserDetails(String username) {
       // return authFeignClient.getUser(username);
       return null;
    }
}