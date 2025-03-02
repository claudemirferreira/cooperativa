package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.domain.TotalVotoDomain;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import com.siscred.cooperativa.infrastructure.persistence.repository.VotoRepository;
import com.siscred.cooperativa.infrastructure.persistence.specification.VotoSpecification;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VotoRepositoryGateway implements VotoGateway {

    private final VotoRepository votoRepository;
    private final ModelMapper modelMapper;

    public VotoRepositoryGateway(VotoRepository votoRepository, ModelMapper modelMapper) {
        this.votoRepository = votoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VotoDomain create(VotoDomain votoDomain) {
        Voto voto = Voto
                .builder()
                .cpf(votoDomain.getCpf())
                .sessao(Sessao.builder().id(votoDomain.getSessao().getId()).build())
                .voto(votoDomain.getVoto())
                .build();
        log.info("{}", voto);
        voto = votoRepository.save(voto);
        votoDomain.setId(voto.getId());
        return votoDomain;
    }

    @Override
    public List<VotoDomain> findBySessaoIdAndCpf(Long sessaoId, String cpf) {
        Specification<Voto> spec = VotoSpecification.filterBySessaoAndCpf(sessaoId, cpf);
        return votoRepository.findAll(spec).stream()
                .map(voto -> modelMapper.map(voto, VotoDomain.class))
                .toList();
    }

    @Override
    public List<TotalVotoDomain> countVotoSesaoAberta() {
        log.info("call countVotoSesaoAberta");
        return votoRepository.countVotoSesaoAberta().stream()
                .map(voto -> modelMapper.map(voto, TotalVotoDomain.class))
                .toList();
    }

}
