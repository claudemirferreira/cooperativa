package com.siscred.cooperativa.infrastructure.controller.dto.request;

import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateVotoRequest {
    private String cpf;
    private Long sessaoId;
    private VotoEnum voto;
}
