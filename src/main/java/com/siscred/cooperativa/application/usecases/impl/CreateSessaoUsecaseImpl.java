package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.SessaoGateway;
import com.siscred.cooperativa.application.usecases.CreateSessaoUsecase;
import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class CreateSessaoUsecaseImpl implements CreateSessaoUsecase {

    private final SessaoGateway sessaoGateway;

    public CreateSessaoUsecaseImpl(SessaoGateway sessaoGateway) {
        this.sessaoGateway = sessaoGateway;
    }

    @Override
    public SessaoDomain execute(Long pautaId, Integer tempoExpiracaoEmMinutos) {
        LocalDateTime start = LocalDateTime.now();
        SessaoDomain sessaoDomain = SessaoDomain
                .builder()
                .inicio(start)
                .fim(Objects.isNull(tempoExpiracaoEmMinutos) ? start.plusMinutes(1) : start.plusMinutes(tempoExpiracaoEmMinutos))
                .pauta(PautaDomain.builder().id(pautaId).build())
                .status(StatusEnum.ABERTO)
                .build();

        log.info("Sessão criada: {}", sessaoDomain);
        return sessaoGateway.save(sessaoDomain);
    }
}
