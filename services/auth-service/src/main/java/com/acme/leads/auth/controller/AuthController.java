package com.acme.leads.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.acme.leads.auth.dto.TokensDTO;
import com.acme.leads.auth.service.UserService;
import com.acme.leads.shared.dto.UserDTO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokensDTO> login(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.login(userDTO), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokensDTO> refresh(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
        return new ResponseEntity<>(userService.refresh(refreshToken), HttpStatus.OK);
    }
}