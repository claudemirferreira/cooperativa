package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.domain.TotalVotoDomain;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import com.siscred.cooperativa.infrastructure.persistence.iquery.TotalVotoDTO;
import com.siscred.cooperativa.infrastructure.persistence.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VotoRepositoryGatewayTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private VotoRepositoryGateway votoRepositoryGateway;

    private Voto votoEntity;
    private VotoDomain votoDomain;
    private TotalVotoDomain totalVotoDomain;
    private TotalVotoDTO totalVotoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup de dados simulados
        Sessao sessao = Sessao.builder().id(1L).build();
        SessaoDomain sessaoDomain = SessaoDomain.builder().id(1L).build();
        votoEntity = Voto.builder().id(1L).cpf("12345678900").sessao(sessao).resposta(VotoEnum.SIM).build();
        votoDomain = VotoDomain.builder().id(1L).cpf("12345678900").sessao(sessaoDomain).voto(VotoEnum.SIM).build();
        totalVotoDomain = TotalVotoDomain.builder().sessaoId(1L).totalSim(1).totalNao(1).pauta("Pauta").build();
        totalVotoDTO = TotalVotoDTO.builder().sessaoId(1L).totalSim(1).totalNao(1).pauta("Pauta").build();
    }

    @Test
    void create_ShouldSaveAndReturnVotoDomain() {
        // Simula a conversão do Entity para Domain
        when(votoRepository.save(any(Voto.class))).thenReturn(votoEntity);
        when(modelMapper.map(votoEntity, VotoDomain.class)).thenReturn(votoDomain);

        VotoDomain result = votoRepositoryGateway.create(votoDomain);

        assertNotNull(result);
        assertEquals(votoDomain.getCpf(), result.getCpf());
        assertEquals(votoDomain.getSessao().getId(), result.getSessao().getId());
        assertEquals(votoDomain.getVoto(), result.getVoto());

        // Verifica se o repositório save foi chamado uma vez
        verify(votoRepository, times(1)).save(any(Voto.class));
    }

    @Test
    void findBySessaoIdAndCpf_ShouldReturnVotoDomainList() {
        // Simula a resposta do repositório
        when(votoRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(votoEntity));
        when(modelMapper.map(votoEntity, VotoDomain.class)).thenReturn(votoDomain);

        List<VotoDomain> result = votoRepositoryGateway.findBySessaoIdAndCpf(1L, "12345678900");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(votoDomain.getCpf(), result.get(0).getCpf());
        assertEquals(votoDomain.getSessao().getId(), result.get(0).getSessao().getId());
        assertEquals(votoDomain.getVoto(), result.get(0).getVoto());

        // Verifica se o método findAll foi chamado uma vez
        verify(votoRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void testCountVotoSesaoAberta() {
        when(votoRepository.countVotoSesaoAberta()).thenReturn(List.of(totalVotoDTO));
        when(modelMapper.map(totalVotoDTO, TotalVotoDomain.class)).thenReturn(totalVotoDomain);

        List<TotalVotoDomain> result = votoRepositoryGateway.countVotoSesaoAberta();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getTotalNao());
        assertEquals(1, result.get(0).getTotalSim());
        assertEquals(1, totalVotoDTO.getTotalNao());
        assertEquals(1, totalVotoDTO.getTotalSim());
        assertEquals("Pauta", totalVotoDTO.getPauta());
        assertEquals(1L, totalVotoDTO.getSessaoId());

        verify(votoRepository, times(1)).countVotoSesaoAberta();
        verify(modelMapper, times(1)).map(totalVotoDTO, TotalVotoDomain.class);
    }

}
