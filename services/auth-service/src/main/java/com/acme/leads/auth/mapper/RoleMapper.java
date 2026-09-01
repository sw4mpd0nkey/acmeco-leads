package com.acme.leads.auth.mapper;

import org.mapstruct.Mapper;

import com.acme.leads.auth.dto.RoleDTO;
import com.acme.leads.auth.model.Role;


@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<Role, RoleDTO, Long> {}