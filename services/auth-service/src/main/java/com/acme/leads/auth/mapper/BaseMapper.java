package com.acme.leads.auth.mapper;

import java.util.List;

import com.acme.leads.auth.dto.BaseDTO;
import com.acme.leads.auth.model.BaseEntity;

public interface BaseMapper<Model extends BaseEntity<ID>, DTO extends BaseDTO<ID>, ID> {
    DTO toDTO(Model model);

    Model toModel(DTO DTO);

    List<DTO> toDTO(List<Model> model);

    List<Model> toModel(List<DTO> DTO);
}