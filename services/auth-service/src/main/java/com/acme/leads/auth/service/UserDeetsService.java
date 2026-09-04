package com.acme.leads.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.acme.leads.auth.mapper.UserMapper;
import com.acme.leads.auth.model.User;
import com.acme.leads.auth.repository.UserRepository;


@Service
class UserDeetsService implements UserDetailsService {

    private @Autowired UserRepository userRepository;
    private @Autowired UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        // TODO: fix this mess
        return null;
    }

}