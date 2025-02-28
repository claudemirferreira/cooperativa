package com.siscred.cooperativa.application.gateways;

import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;

import java.util.List;

public interface VotoGateway {
    VotoDomain create(VotoDomain votoDomain);
    List<Voto> findBySessaoIdAndCpf(Long sessaoId, String cpf);
}
