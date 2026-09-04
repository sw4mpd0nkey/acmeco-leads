package com.acme.leads.shared.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.acme.leads.shared.dto.BaseDTO;
import com.acme.leads.shared.exception.NotFoundException;
import com.acme.leads.shared.mapper.BaseMapper;
import com.acme.leads.shared.model.BaseEntity;
import com.acme.leads.shared.repository.BaseRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseService<Model extends BaseEntity<ID>, DTO extends BaseDTO<ID>, ID> {

    private @Autowired BaseRepository<Model, ID> repository;
    private @Autowired BaseMapper<Model, DTO, ID> mapper;

    public List<DTO> findAll() {
        return mapper.toDTO((List<Model>) repository.findAll());
    }

    public Page<DTO> findAll(Pageable pageable, String search) {
        return repository.findContaining(pageable, "%" + search + "%").map(mapper::toDTO);
    }

    public List<DTO> findById(Set<ID> ids) {
        boolean anyNotFound = ids.stream().anyMatch((id) -> !repository.existsById(id));
        if (anyNotFound) {
            throw new NotFoundException("ID not found");
        }
        return mapper.toDTO((List<Model>) repository.findAllById(ids));
    }

    @Transactional
    public DTO save(DTO DTO) {
        ID id = DTO.getId();
        if (id != null && !repository.existsById(id)) {
            throw new NotFoundException("ID not found");
        }

        return this.forceSave(DTO);
    }

    @Transactional
    public DTO forceSave(DTO DTO) {
        Model model = mapper.toModel(DTO);
        return mapper.toDTO(repository.save(model));
    }

    @Transactional
    public void delete(Set<ID> ids) {
        boolean anyNotFound = ids.stream().anyMatch((id) -> !repository.existsById(id));
        if (anyNotFound) {
            throw new NotFoundException("ID not found");
        }
        repository.softDeleteByIds(ids);
    }
}