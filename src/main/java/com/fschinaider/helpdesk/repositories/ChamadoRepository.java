package com.fschinaider.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fschinaider.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
