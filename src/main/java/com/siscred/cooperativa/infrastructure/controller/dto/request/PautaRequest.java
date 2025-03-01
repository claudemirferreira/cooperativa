package com.siscred.cooperativa.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record PautaRequest(@NotEmpty(message = "Nome é obrigatório") String nome) {

}