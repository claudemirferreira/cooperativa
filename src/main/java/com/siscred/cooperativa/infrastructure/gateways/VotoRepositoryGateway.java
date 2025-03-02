package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.domain.TotalVotoDomain;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.mapper.TotalVotoMapper;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import com.siscred.cooperativa.infrastructure.persistence.repository.VotoRepository;
import com.siscred.cooperativa.infrastructure.persistence.specification.VotoSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VotoRepositoryGateway implements VotoGateway {

    private final VotoRepository votoRepository;
    private final TotalVotoMapper totalVotoMapper;

    public VotoRepositoryGateway(VotoRepository votoRepository, TotalVotoMapper totalVotoMapper) {
        this.votoRepository = votoRepository;
        this.totalVotoMapper = totalVotoMapper;
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

    @Override
    public List<Voto> findBySessaoIdAndCpf(Long sessaoId, String cpf) {
        Specification<Voto> spec = VotoSpecification.filterBySessaoAndCpf(sessaoId, cpf);
        return votoRepository.findAll(spec);
    }

    @Override
    public List<TotalVotoDomain> countVotoSesaoAberta() {
        log.info("call countVotoSesaoAberta");
        return totalVotoMapper.toDomain(votoRepository.countVotoSesaoAberta());
    }



}
