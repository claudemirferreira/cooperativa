package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.application.gateways.SessaoVotacaoGateway;
import com.siscred.cooperativa.domain.SessaoVotacaoDomain;
import com.siscred.cooperativa.infrastructure.persistence.entity.SessaoVotacao;
import com.siscred.cooperativa.infrastructure.persistence.repository.SessaoVotacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SessaoVotacaoRepositoryGateway implements SessaoVotacaoGateway {

    private final SessaoVotacaoRepository sessaoVotacaoRepository;

    private final ModelMapper modelMapper;

    public SessaoVotacaoRepositoryGateway(SessaoVotacaoRepository sessaoVotacaoRepository, ModelMapper modelMapper) {
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SessaoVotacaoDomain create(SessaoVotacaoDomain sessaoVotacaoDomain) {
        SessaoVotacao entity = modelMapper.map(sessaoVotacaoDomain, SessaoVotacao.class);
        sessaoVotacaoRepository.save(entity);
        return modelMapper.map(entity, SessaoVotacaoDomain.class);
    }
}
