package com.siscred.cooperativa.infrastructure.controller.dto.response;

import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;

import java.time.LocalDateTime;

public record CreateSessaoVotacaoResponse(
        Long id,
        LocalDateTime inicio,
        LocalDateTime fim,
        PautaDomain pauta,
        StatusEnum status) {
}