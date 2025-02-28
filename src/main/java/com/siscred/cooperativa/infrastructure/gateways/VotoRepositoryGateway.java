package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import com.siscred.cooperativa.infrastructure.persistence.repository.VotoRepository;
import com.siscred.cooperativa.infrastructure.persistence.specification.VotoSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotoRepositoryGateway implements VotoGateway {

    private final VotoRepository votoRepository;

    public VotoRepositoryGateway(VotoRepository votoRepository) {
        this.votoRepository = votoRepository;
    }

    @Override
    public VotoDomain create(VotoDomain votoDomain) {
        var entity = votoRepository.save(Voto
                .builder()
                .cpf(votoDomain.getCpf())
                .sessao(Sessao.builder().id(votoDomain.getSessao().getId()).build())
                .voto(votoDomain.getVoto())
                .build());
        votoDomain.setId(entity.getId());
        return votoDomain;
    }

    public List<Voto> findBySessaoIdAndCpf(Long sessaoId, String cpf) {
        Specification<Voto> spec = VotoSpecification.filterBySessaoAndCpf(sessaoId, cpf);
        return votoRepository.findAll(spec);
    }
}
