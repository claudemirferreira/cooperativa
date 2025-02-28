package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import com.siscred.cooperativa.infrastructure.persistence.repository.VotoRepository;
import com.siscred.cooperativa.infrastructure.persistence.specification.VotoSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotoRepositoryGatewayTest {

    @Mock
    private VotoRepository votoRepository;

    @InjectMocks
    private VotoRepositoryGateway votoRepositoryGateway;

    private Voto votoEntity;
    private VotoDomain votoDomain;

    @BeforeEach
    void setUp() {
        votoEntity = Voto.builder()
                .id(1L)
                .cpf("12345678901")
                .sessao(Sessao.builder().id(10L).build())
                .voto(VotoEnum.SIM)
                .build();

        votoDomain = VotoDomain.builder()
                .id(1L)
                .cpf("12345678901")
                .sessao(com.siscred.cooperativa.domain.SessaoDomain.builder().id(10L).build())
                .voto(VotoEnum.SIM)
                .build();
    }

    @Test
    void shouldCreateVotoSuccessfully() {
        // Arrange
        when(votoRepository.save(any(Voto.class))).thenReturn(votoEntity);

        // Act
        VotoDomain result = votoRepositoryGateway.create(votoDomain);

        // Assert
        assertNotNull(result);
        assertEquals(votoEntity.getId(), result.getId());
        assertEquals(votoEntity.getCpf(), result.getCpf());
        assertEquals(votoEntity.getSessao().getId(), result.getSessao().getId());
        assertEquals(votoEntity.getVoto(), result.getVoto());

        // Verify
        verify(votoRepository, times(1)).save(any(Voto.class));
    }

    @Test
    void shouldFindBySessaoIdAndCpf() {
        // Arrange
        Long sessaoId = 10L;
        String cpf = "12345678901";

        Specification<Voto> specMock = VotoSpecification.filterBySessaoAndCpf(sessaoId, cpf);
        when(votoRepository.findAll(any(Specification.class))).thenReturn(List.of(votoEntity));

        // Act
        List<Voto> result = votoRepositoryGateway.findBySessaoIdAndCpf(sessaoId, cpf);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(cpf, result.get(0).getCpf());

        // Verify
        verify(votoRepository, times(1)).findAll(any(Specification.class));
    }
}
