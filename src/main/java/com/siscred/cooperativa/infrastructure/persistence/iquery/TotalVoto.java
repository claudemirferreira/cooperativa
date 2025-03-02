package com.siscred.cooperativa.infrastructure.persistence.iquery;

public interface TotalVoto {
    Integer getTotalSim();
    Integer getTotalNao();
    String getPauta();
    Long getSessaoId();
}
