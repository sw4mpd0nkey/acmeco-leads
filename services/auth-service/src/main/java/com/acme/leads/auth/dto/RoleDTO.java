package com.acme.leads.auth.dto;

import org.springframework.security.core.GrantedAuthority;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class RoleDTO extends BaseDTO<Long> implements GrantedAuthority {
    @NotBlank(message = "Authority is mandatory")
    private String authority;
}