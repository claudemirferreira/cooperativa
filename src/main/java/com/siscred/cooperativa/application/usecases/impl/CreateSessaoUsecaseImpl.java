package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.SessaoGateway;
import com.siscred.cooperativa.application.usecases.CreateSessaoUsecase;
import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Slf4j
@Service
public class CreateSessaoUsecaseImpl implements CreateSessaoUsecase {

    private final SessaoGateway sessaoGateway;

    private static final String TIME_ZONE_SP = "America/Sao_Paulo";

    public CreateSessaoUsecaseImpl(SessaoGateway sessaoGateway) {
        this.sessaoGateway = sessaoGateway;
    }

    @Override
    public SessaoDomain execute(Long pautaId, Integer tempoExpiracaoEmMinutos) {
        OffsetDateTime start = OffsetDateTime.now(ZoneId.of(TIME_ZONE_SP));
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
