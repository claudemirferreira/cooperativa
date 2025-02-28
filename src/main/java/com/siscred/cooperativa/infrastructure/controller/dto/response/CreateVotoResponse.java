package com.siscred.cooperativa.infrastructure.controller.dto.response;

import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateVotoResponse {
    private String cpf;
    private Long sessaoId;
    private VotoEnum voto;
}
