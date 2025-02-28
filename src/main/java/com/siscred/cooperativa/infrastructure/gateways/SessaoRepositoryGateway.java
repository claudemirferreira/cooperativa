package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.application.gateways.SessaoGateway;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.repository.SessaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SessaoRepositoryGateway implements SessaoGateway {

    private final SessaoRepository sessaoRepository;

    private final ModelMapper modelMapper;

    public SessaoRepositoryGateway(SessaoRepository sessaoRepository, ModelMapper modelMapper) {
        this.sessaoRepository = sessaoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SessaoDomain create(SessaoDomain sessaoDomain) {
        Sessao entity = modelMapper.map(sessaoDomain, Sessao.class);
        sessaoRepository.save(entity);
        return modelMapper.map(entity, SessaoDomain.class);
    }
}
