package com.siscred.cooperativa.application.gateways;

import com.siscred.cooperativa.domain.PautaDomain;

public interface PautaGateway {
    PautaDomain create(PautaDomain pautaDomain);
}
