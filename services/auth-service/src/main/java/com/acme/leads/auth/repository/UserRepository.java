package com.acme.leads.auth.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.acme.leads.auth.model.User;
import com.acme.leads.shared.repository.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    @Override
    @Query(
            "select x from #{#entityName} x where x.deleted = false "
                    + "and (cast(x.id as string) like :search or x.username like :search)")
    Page<User> findContaining(Pageable pageable, String search);

    Optional<User> findByUsername(String username);
}
