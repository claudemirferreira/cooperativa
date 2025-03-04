package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.domain.TotalVotoDomain;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import com.siscred.cooperativa.infrastructure.persistence.iquery.ITotalVoto;
import com.siscred.cooperativa.infrastructure.persistence.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Sessao sessao = Sessao.builder().id(1L).build();
        SessaoDomain sessaoDomain = SessaoDomain.builder().id(1L).build();
        votoEntity = Voto.builder().id(1L).cpf("12345678900").sessao(sessao).resposta(VotoEnum.SIM).build();
        votoDomain = VotoDomain.builder().id(1L).cpf("12345678900").sessao(sessaoDomain).voto(VotoEnum.SIM).build();
    }

    @Test
    void create_ShouldSaveAndReturnVotoDomain() {
        when(votoRepository.save(any(Voto.class))).thenReturn(votoEntity);
        when(modelMapper.map(votoEntity, VotoDomain.class)).thenReturn(votoDomain);

        VotoDomain result = votoRepositoryGateway.create(votoDomain);

        assertNotNull(result);
        assertEquals(votoDomain.getCpf(), result.getCpf());
        assertEquals(votoDomain.getSessao().getId(), result.getSessao().getId());
        assertEquals(votoDomain.getVoto(), result.getVoto());

        verify(votoRepository, times(1)).save(any(Voto.class));
    }

    @Test
    void findBySessaoIdAndCpf_ShouldReturnVotoDomainList() {
        when(votoRepository.findAll(any(Specification.class))).thenReturn(List.of(votoEntity));
        when(modelMapper.map(votoEntity, VotoDomain.class)).thenReturn(votoDomain);

        List<VotoDomain> result = votoRepositoryGateway.findBySessaoIdAndCpf(1L, "12345678900");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(votoDomain.getCpf(), result.get(0).getCpf());
        assertEquals(votoDomain.getSessao().getId(), result.get(0).getSessao().getId());
        assertEquals(votoDomain.getVoto(), result.get(0).getVoto());

        verify(votoRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void testCountVotoSesaoAberta() {
        ITotalVoto iTotalVoto = mock(ITotalVoto.class);
        when(iTotalVoto.getPauta()).thenReturn("Pauta 1");
        when(iTotalVoto.getSessaoId()).thenReturn(1L);
        when(iTotalVoto.getTotalNao()).thenReturn(10);
        when(iTotalVoto.getTotalSim()).thenReturn(15);

        List<ITotalVoto> totalVotos = List.of(iTotalVoto); // Garante que a lista não é nula

        when(votoRepository.countVotoSesaoAberta()).thenReturn(totalVotos);

        List<TotalVotoDomain> result = votoRepositoryGateway.countVotoSesaoAberta();

        verify(votoRepository).countVotoSesaoAberta();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

}
