package com.siscred.cooperativa.infrastructure.controller.dto.response;

import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;

import java.time.OffsetDateTime;

public record CreateSessaoVotacaoResponse(
        Long id,
        OffsetDateTime inicio,
        OffsetDateTime fim,
        PautaDomain pauta,
        StatusEnum status) {
}