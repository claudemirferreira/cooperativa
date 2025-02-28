package com.siscred.cooperativa.infrastructure.persistence.repository;

import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

}
