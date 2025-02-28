package com.siscred.cooperativa.infrastructure.persistence.repository;

import com.siscred.cooperativa.infrastructure.persistence.entity.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

}
