package com.siscred.cooperativa.domain;

import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessaoDomain {
    private Long id;
    private OffsetDateTime inicio;
    private OffsetDateTime fim;
    private PautaDomain pauta;
    private StatusEnum status;

}
