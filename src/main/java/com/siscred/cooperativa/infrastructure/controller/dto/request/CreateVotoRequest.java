package com.siscred.cooperativa.infrastructure.controller.dto.request;

import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import jakarta.validation.constraints.NotNull;

public record CreateVotoRequest(
        @NotNull(message = "cpf é obrigatório") String cpf,
        @NotNull(message = "sessaoId é obrigatório") Long sessaoId,
        @NotNull(message = "voto é obrigatório") VotoEnum voto) {
}
