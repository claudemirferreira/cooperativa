package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.domain.TotalVotoDomain;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import com.siscred.cooperativa.infrastructure.persistence.iquery.ITotalVoto;
import com.siscred.cooperativa.infrastructure.persistence.iquery.TotalVotoDTO;
import com.siscred.cooperativa.infrastructure.persistence.repository.VotoRepository;
import com.siscred.cooperativa.infrastructure.persistence.specification.VotoSpecification;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                .resposta(votoDomain.getVoto())
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

        List<ITotalVoto> lista = votoRepository.countVotoSesaoAberta();
        List<TotalVotoDTO> listaDTO = toDTO(lista);
        return listaDTO.stream()
                .map(voto -> modelMapper.map(voto, TotalVotoDomain.class))
                .toList();
    }

    private TotalVotoDTO toDTO(ITotalVoto iTotalVoto) {
        return TotalVotoDTO
                .builder()
                .pauta(iTotalVoto.getPauta())
                .sessaoId(iTotalVoto.getSessaoId())
                .totalNao(iTotalVoto.getTotalNao())
                .totalSim(iTotalVoto.getTotalSim())
                .build();
    }

    private List<TotalVotoDTO> toDTO(List<ITotalVoto> lista) {
        List<TotalVotoDTO> listaDTO = new ArrayList<>();
        lista.forEach(item -> listaDTO.add(toDTO(item)));
        return listaDTO;
    }

}
