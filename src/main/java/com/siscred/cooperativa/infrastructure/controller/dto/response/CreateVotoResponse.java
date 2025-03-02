package com.siscred.cooperativa.infrastructure.controller.dto.response;

import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;

public record CreateVotoResponse(
        String cpf,
        Long sessaoId,
        VotoEnum voto) {
}
