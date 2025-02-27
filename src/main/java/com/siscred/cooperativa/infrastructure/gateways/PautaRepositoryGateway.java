package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.application.gateways.PautaGateway;
import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.infrastructure.persistence.entity.Pauta;
import com.siscred.cooperativa.infrastructure.persistence.repository.PautaRepository;
import org.springframework.stereotype.Service;

@Service
public class PautaRepositoryGateway implements PautaGateway {

    private final PautaRepository pautaRepository;

    public PautaRepositoryGateway(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    @Override
    public PautaDomain create(PautaDomain pautaDomain) {
        var entity =  pautaRepository.save(Pauta.builder().nome(pautaDomain.getNome()).build());
        return PautaDomain.builder().id(entity.getId()).nome(entity.getNome()).build();
    }
}
