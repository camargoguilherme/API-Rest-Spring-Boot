package com.teste.xbrain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.teste.xbrain.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	Page<Client> findAll(Pageable pageable);
}
