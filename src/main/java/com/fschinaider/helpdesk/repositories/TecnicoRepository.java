package com.fschinaider.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fschinaider.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
