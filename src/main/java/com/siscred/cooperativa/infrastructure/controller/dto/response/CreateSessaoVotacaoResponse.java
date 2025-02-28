package com.siscred.cooperativa.infrastructure.controller.dto.response;

import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSessaoVotacaoResponse {
    private Long id;
    private OffsetDateTime inicio;
    private OffsetDateTime fim;
    private PautaDomain pauta;
    private StatusEnum status;
}
