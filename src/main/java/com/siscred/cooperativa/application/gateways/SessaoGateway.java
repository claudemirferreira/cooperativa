package com.siscred.cooperativa.application.gateways;

import com.siscred.cooperativa.domain.SessaoDomain;

public interface SessaoGateway {
    SessaoDomain save(SessaoDomain sessaoDomain);

    SessaoDomain findById(Long id);

    Boolean existSessaoAberta(Long sessaoId);
}
