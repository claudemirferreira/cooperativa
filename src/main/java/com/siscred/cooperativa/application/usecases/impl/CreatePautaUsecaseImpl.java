package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.PautaGateway;
import com.siscred.cooperativa.application.usecases.CreatePautaUsecase;
import com.siscred.cooperativa.domain.PautaDomain;
import org.springframework.stereotype.Service;

@Service
public class CreatePautaUsecaseImpl implements CreatePautaUsecase {

    private final PautaGateway pautaGateway;

    public CreatePautaUsecaseImpl(PautaGateway pautaGateway) {
        this.pautaGateway = pautaGateway;
    }

    @Override
    public PautaDomain execute(PautaDomain pautaDomain) {
        return this.pautaGateway.create(pautaDomain);
    }

}
