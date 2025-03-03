package com.siscred.cooperativa.domain;

import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessaoDomain {
    private Long id;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private PautaDomain pauta;
    private StatusEnum status;

}
