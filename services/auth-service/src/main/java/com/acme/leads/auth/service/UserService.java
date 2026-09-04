package com.acme.leads.auth.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.leads.auth.dto.TokensDTO;
import com.acme.leads.auth.mapper.UserMapper;
import com.acme.leads.auth.model.User;
import com.acme.leads.auth.repository.UserRepository;
import com.acme.leads.auth.security.TokenGenerator;
import com.acme.leads.shared.dto.UserDTO;
import com.acme.leads.shared.dto.UserDetailsDTO;
import com.acme.leads.shared.service.BaseService;
import static com.acme.leads.shared.util.SecurityUtils.BEARER_PREFIX;
import static com.acme.leads.shared.util.SecurityUtils.ROLE_ADMIN;
import static com.acme.leads.shared.util.SecurityUtils.getUsername;
import static com.acme.leads.shared.util.SecurityUtils.hasAuthority;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

@Service
public class UserService extends BaseService<User, UserDetailsDTO, Long> {

    private @Autowired UserRepository repository;
    private @Autowired UserMapper mapper;
    private @Autowired UserDetailsService userDetailsService;
    private @Autowired TokenGenerator tokenGenerator;
    private @Autowired AuthenticationManager authenticationManager;
    private @Autowired PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public UserDetailsDTO save(UserDetailsDTO userDetailsDTO) {
        userDetailsDTO.setPassword(passwordEncoder.encode(userDetailsDTO.getPassword()));
        userDetailsDTO.setAccountNonExpired(true);
        userDetailsDTO.setAccountNonLocked(true);
        userDetailsDTO.setCredentialsNonExpired(true);
        userDetailsDTO.setEnabled(true);
        return super.save(userDetailsDTO);
    }

    @Transactional
    public UserDetailsDTO update(UserDetailsDTO userDetailsDTO) {
        User existingUser =
                repository
                        .findById(userDetailsDTO.getId())
                        .orElseThrow(() -> new NotFoundException("User not found"));
        if (userDetailsDTO.getUsername() != null) {
            existingUser.setUsername(userDetailsDTO.getUsername());
        }
        if (userDetailsDTO.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(userDetailsDTO.getPassword()));
        }

        return this.mapper.toDTO(this.repository.save(existingUser));
    }

    public List<UserDTO> findByIdPublic(Set<Long> id) {
        List<User> users = (List<User>) this.repository.findAllById(id);
        if (users.isEmpty()) {
            throw new NotFoundException("User id not found");
        }
        return this.mapper.userToUserDTOList(users);
    }

    public UserDetailsDTO findByUsername(String username) throws UsernameNotFoundException {
        if (!getUsername().equals(username) && !hasAuthority(ROLE_ADMIN)) {
            throw new ForbiddenException("You are not allowed to view this user's details");
        }

        return (UserDetailsDTO) userDetailsService.loadUserByUsername(username);
    }

    public Long findIdByUsername(String username) {
        return this.repository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Username not found"))
                .getId();
    }

    public TokensDTO login(UserDTO userDTO) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        userDTO.getUsername(), userDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String username = userDTO.getUsername();
        return new TokensDTO(
                tokenGenerator.generateAccessToken(username),
                tokenGenerator.generateRefreshToken(username));
    }

    public TokensDTO refresh(String refreshToken) {
        refreshToken = refreshToken.substring(BEARER_PREFIX.length());
        return new TokensDTO(tokenGenerator.refreshAccessToken(refreshToken), refreshToken);
    }
}