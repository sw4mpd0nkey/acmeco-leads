package com.acme.leads.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.acme.leads.auth.model.Role;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
    @Override
    @Query(
            "select x from #{#entityName} x where x.deleted = false "
                    + "and (cast(x.id as string) like :search or x.authority like :search)")
    Page<Role> findContaining(Pageable pageable, String search);
}