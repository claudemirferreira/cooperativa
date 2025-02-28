package com.siscred.cooperativa.application.gateways;

import com.siscred.cooperativa.domain.SessaoDomain;

public interface SessaoGateway {
    SessaoDomain create(SessaoDomain sessaoDomain);
}
