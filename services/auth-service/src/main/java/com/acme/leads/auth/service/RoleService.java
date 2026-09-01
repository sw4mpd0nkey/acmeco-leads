package com.acme.leads.auth.service;

import org.springframework.stereotype.Service;

import com.acme.leads.auth.dto.RoleDTO;
import com.acme.leads.auth.mapper.RoleMapper;
import com.acme.leads.auth.model.Role;
import com.acme.leads.auth.repository.RoleRepository;

@Service
public class RoleService extends BaseService<Role, RoleDTO, Long> {
    private final RoleRepository repository;
    private final RoleMapper mapper;

    public RoleService(RoleRepository repository, RoleMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}