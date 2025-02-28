package com.siscred.cooperativa.infrastructure.persistence.repository;

import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VotoRepository extends JpaRepository<Voto, Long>, JpaSpecificationExecutor<Voto> {

}
