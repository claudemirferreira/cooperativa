package com.siscred.cooperativa.application.gateways;

import com.siscred.cooperativa.domain.TotalVotoDomain;

public interface ContabilizarVotoMensageriaGateway {
    void send(TotalVotoDomain totalVotoDomain);
}
