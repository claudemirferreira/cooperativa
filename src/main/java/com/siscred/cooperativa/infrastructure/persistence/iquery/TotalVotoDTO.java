package com.siscred.cooperativa.infrastructure.persistence.iquery;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class TotalVotoDTO implements ITotalVoto {
    private Long sessaoId;
    private String pauta;
    private Integer totalSim;
    private Integer totalNao;

    @Override
    public Integer getTotalSim() {
        return totalSim;
    }

    @Override
    public Integer getTotalNao() {
        return totalNao;
    }

    @Override
    public String getPauta() {
        return pauta;
    }

    @Override
    public Long getSessaoId() {
        return sessaoId;
    }
}
