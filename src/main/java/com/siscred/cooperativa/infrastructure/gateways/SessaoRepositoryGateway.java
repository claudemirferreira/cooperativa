package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.application.gateways.SessaoGateway;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import com.siscred.cooperativa.infrastructure.persistence.entity.Pauta;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.repository.PautaRepository;
import com.siscred.cooperativa.infrastructure.persistence.repository.SessaoRepository;
import com.siscred.cooperativa.infrastructure.persistence.specification.SessaoSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SessaoRepositoryGateway implements SessaoGateway {

    private final SessaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;

    private final ModelMapper modelMapper;

    public SessaoRepositoryGateway(SessaoRepository sessaoRepository, PautaRepository pautaRepository, ModelMapper modelMapper) {
        this.sessaoRepository = sessaoRepository;
        this.pautaRepository = pautaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SessaoDomain save(SessaoDomain sessaoDomain) {
        Sessao entity = mapper(sessaoDomain);
        sessaoRepository.save(entity);
        return modelMapper.map(entity, SessaoDomain.class);
    }

    @Override
    public SessaoDomain findById(Long id) {
        Sessao entity = sessaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão com ID " + id + " não encontrada"));
        return modelMapper.map(entity, SessaoDomain.class);
    }

    @Override
    public Boolean existSessaoAberta(Long sessaoId) {
        Specification<Sessao> spec = SessaoSpecification.filterBySessaoIdAndStatus(sessaoId, StatusEnum.ABERTO);
        return sessaoRepository.findOne(spec).map(sessao -> modelMapper.map(sessao, SessaoDomain.class)).isPresent();
    }

    private Sessao mapper(SessaoDomain sessaoDomain) {
        Sessao entity = modelMapper.map(sessaoDomain, Sessao.class);
        Pauta pauta = pautaRepository.findById(sessaoDomain.getPauta().getId())
                .orElseThrow(() -> new EntityNotFoundException("Pauta not found with id: " + sessaoDomain.getId()));
        entity.setPauta( pauta);
        return entity;
    }

}
