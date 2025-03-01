package com.siscred.cooperativa.application.gateways;

import com.siscred.cooperativa.domain.VotoDomain;

public interface VotoMensageriaGateway {
    void send(VotoDomain votoDomain);
}
