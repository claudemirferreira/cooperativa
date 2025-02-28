package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.SessaoVotacaoGateway;
import com.siscred.cooperativa.application.usecases.CreateSessaoVotacaoUsecase;
import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.domain.SessaoVotacaoDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Objects;

@Slf4j
@Service
public class CreateSessaoVotacaoUsecaseImpl implements CreateSessaoVotacaoUsecase {

    private final SessaoVotacaoGateway sessaoVotacaoGateway;

    public CreateSessaoVotacaoUsecaseImpl(SessaoVotacaoGateway sessaoVotacaoGateway) {
        this.sessaoVotacaoGateway = sessaoVotacaoGateway;
    }

    @Override
    public SessaoVotacaoDomain execute(Long pautaId, Integer tempoExpiracaoEmMinutos) {
        OffsetDateTime start = OffsetDateTime.now();
        SessaoVotacaoDomain sessaoVotacaoDomain = SessaoVotacaoDomain
                .builder()
                .inicio(start)
                .fim(Objects.isNull( tempoExpiracaoEmMinutos) ? start.plusMinutes(1) : start.plusMinutes(tempoExpiracaoEmMinutos))
                .pauta(PautaDomain.builder().id(pautaId).build())
                .status(StatusEnum.ABERTO)
                .build();
        log.info("{}", sessaoVotacaoDomain);
        return sessaoVotacaoGateway.create(sessaoVotacaoDomain);
    }

}
