package com.siscred.cooperativa.infrastructure.persistence.iquery;

public interface ITotalVoto {
    Integer getTotalSim();
    Integer getTotalNao();
    String getPauta();
    Long getSessaoId();
}
