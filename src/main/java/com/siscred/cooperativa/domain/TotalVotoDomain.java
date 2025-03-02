package com.siscred.cooperativa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalVotoDomain {
    private Long sessaoId;
    private String pauta;
    private Integer totalSim;
    private Integer totalNao;
}
