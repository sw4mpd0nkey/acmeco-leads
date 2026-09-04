package com.acme.leads.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.acme.leads.shared.client.AuthFeignClient;
import com.acme.leads.shared.security.AuthenticationTokenFilter;
import com.acme.leads.shared.security.JwtHelper;

@Component
public class AuthTokenFilter extends AuthenticationTokenFilter {

    private @Autowired JwtHelper jwtHelper;
    private @Autowired UserDetailsService userDetailsService;

    public AuthTokenFilter(AuthFeignClient authFeignClient, JwtHelper jwtHelper) {
        super(authFeignClient, jwtHelper);
    }


    @Override
    protected UserDetails getUserDetails(String username) {
        return userDetailsService.loadUserByUsername(username);
    }
}