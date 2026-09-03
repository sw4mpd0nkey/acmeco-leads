package com.acme.leads.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.acme.leads.shared.security.AuthenticationTokenFilter;
import com.acme.leads.shared.security.TokenUtils;

@Component
public class AuthTokenFilter extends AuthenticationTokenFilter {

    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;

    public AuthTokenFilter(TokenUtils tokenUtils, UserDetailsService userDetailsService) {
        
        super(null, tokenUtils);
        this.userDetailsService = userDetailsService;
        this.tokenUtils = tokenUtils;
    }

    @Override
    protected UserDetails getUserDetails(String username) {
        return userDetailsService.loadUserByUsername(username);
    }
}