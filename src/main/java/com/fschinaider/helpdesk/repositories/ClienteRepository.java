package com.fschinaider.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fschinaider.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
