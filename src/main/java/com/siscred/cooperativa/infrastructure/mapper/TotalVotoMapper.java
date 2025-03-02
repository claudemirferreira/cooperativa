package com.siscred.cooperativa.infrastructure.mapper;

import com.siscred.cooperativa.domain.TotalVotoDomain;
import com.siscred.cooperativa.infrastructure.persistence.iquery.TotalVoto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TotalVotoMapper {

    public TotalVotoDomain toDomain(TotalVoto totalVoto) {
        if (totalVoto == null) {
            return null;
        }
        
        return TotalVotoDomain.builder()
                .pauta(totalVoto.getPauta())
                .sessaoId(totalVoto.getSessaoId())
                .totalSim(totalVoto.getTotalSim())
                .totalNao(totalVoto.getTotalNao())
                .build();
    }

    public List<TotalVotoDomain> toDomain(List<TotalVoto> listaTotalVoto) {
        return listaTotalVoto == null ? List.of() :
                listaTotalVoto.stream()
                        .map(this::toDomain)
                        .collect(Collectors.toList());
    }
}
