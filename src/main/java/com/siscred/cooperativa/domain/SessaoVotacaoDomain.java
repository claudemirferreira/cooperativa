package com.siscred.cooperativa.domain;

import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessaoVotacaoDomain {
    private Long id;
    private OffsetDateTime inicio;
    private OffsetDateTime fim;
    private PautaDomain pauta;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusEnum status;

}
