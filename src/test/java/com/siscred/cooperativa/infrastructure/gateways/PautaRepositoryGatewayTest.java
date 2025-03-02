package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.infrastructure.persistence.entity.Pauta;
import com.siscred.cooperativa.infrastructure.persistence.repository.PautaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PautaRepositoryGatewayTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaRepositoryGateway pautaRepositoryGateway;

    @BeforeEach
    void setUp() {
        pautaRepositoryGateway = new PautaRepositoryGateway(pautaRepository);
    }

    @Test
    void shouldCreatePautaSuccessfully() {
        // Arrange
        PautaDomain pautaDomain = PautaDomain.builder().nome("Pauta Teste").build();
        Pauta pautaEntity = Pauta.builder().id(1L).nome("Pauta Teste").build();

        when(pautaRepository.save(any(Pauta.class))).thenReturn(pautaEntity);

        // Act
        PautaDomain result = pautaRepositoryGateway.create(pautaDomain);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pauta Teste", result.getNome());
        verify(pautaRepository, times(1)).save(any(Pauta.class));
    }
}
