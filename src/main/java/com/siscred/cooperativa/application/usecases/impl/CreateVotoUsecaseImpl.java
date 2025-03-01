package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.application.gateways.VotoMensageriaGateway;
import com.siscred.cooperativa.application.usecases.CreateVotoUsecase;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.exception.ExistVotoCPFException;
import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import org.springframework.stereotype.Service;

@Service
public class CreateVotoUsecaseImpl implements CreateVotoUsecase {

    private final VotoGateway votoGateway;
    private final VotoMensageriaGateway votoMensageriaGateway;

    public CreateVotoUsecaseImpl(VotoGateway votoGateway, VotoMensageriaGateway votoMensageriaGateway) {
        this.votoGateway = votoGateway;
        this.votoMensageriaGateway = votoMensageriaGateway;
    }

    @Override
    public VotoDomain execute(String cpf, Long sessaoId, VotoEnum voto) {
        VotoDomain votoDomain = buildVotoDomain(cpf, sessaoId, voto);
        votoDomain.validateCPF();
        ensureVotoDoesNotExist(sessaoId, cpf);
        votoMensageriaGateway.send(votoDomain);
        return votoDomain;
    }

    private VotoDomain buildVotoDomain(String cpf, Long sessaoId, VotoEnum voto) {
        return VotoDomain.builder()
                .sessao(SessaoDomain.builder().id(sessaoId).build())
                .cpf(cpf)
                .voto(voto)
                .build();
    }

    private void ensureVotoDoesNotExist(Long sessaoId, String cpf) {
        if (!votoGateway.findBySessaoIdAndCpf(sessaoId, cpf).isEmpty()) {
            throw new ExistVotoCPFException("Já existe um voto para o CPF informado.");
        }
    }
}
