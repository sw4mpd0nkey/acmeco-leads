package com.acme.leads.shared.mapper;

import java.util.List;

import com.acme.leads.shared.dto.BaseDTO;
import com.acme.leads.shared.model.BaseEntity;


public interface BaseMapper<Model extends BaseEntity<ID>, DTO extends BaseDTO<ID>, ID> {

    DTO toDTO(Model model);

    Model toModel(DTO DTO);

    List<DTO> toDTO(List<Model> model);

    List<Model> toModel(List<DTO> DTO);
}