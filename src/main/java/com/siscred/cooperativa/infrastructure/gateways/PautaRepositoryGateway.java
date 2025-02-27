package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.application.gateways.PautaGateway;
import com.siscred.cooperativa.domain.PautaDomain;
import org.springframework.stereotype.Service;

@Service
public class PautaRepositoryGateway implements PautaGateway {

    @Override
    public PautaDomain create(PautaDomain pautaDomain) {
        return null;
    }
}
