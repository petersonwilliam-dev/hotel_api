package com.hotel.hotel.domain.client;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findAllByDeletedFalse(Pageable pagination);
}
