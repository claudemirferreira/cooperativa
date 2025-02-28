package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.SessaoGateway;
import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateSessaoVotoUsecaseImplTest {

    @InjectMocks
    private CreateSessaoUsecaseImpl createSessaoVotacaoUsecase;

    @Mock
    private SessaoGateway sessaoGateway;

    private Long pautaId;
    private OffsetDateTime start;

    @BeforeEach
    void setUp() {
        pautaId = 1L;
        start = OffsetDateTime.now();
    }

    @Test
    void shouldCreateSessaoVotacaoWithDefaultTime() {
        SessaoDomain expectedSessao = SessaoDomain.builder()
                .inicio(start)
                .fim(start.plusMinutes(1)) // Tempo padrão
                .pauta(PautaDomain.builder().id(pautaId).build())
                .status(StatusEnum.ABERTO)
                .build();

        when(sessaoGateway.create(any(SessaoDomain.class))).thenReturn(expectedSessao);

        SessaoDomain result = createSessaoVotacaoUsecase.execute(pautaId, null);

        assertNotNull(result);
        assertEquals(1, result.getFim().getMinute() - result.getInicio().getMinute()); // 1 minuto padrão
        assertEquals(StatusEnum.ABERTO, result.getStatus());
        assertEquals(pautaId, result.getPauta().getId());

        verify(sessaoGateway, times(1)).create(any(SessaoDomain.class));
    }

    @Test
    void shouldCreateSessaoVotacaoWithCustomTime() {
        int customMinutes = 5;

        SessaoDomain expectedSessao = SessaoDomain.builder()
                .inicio(start)
                .fim(start.plusMinutes(customMinutes))
                .pauta(PautaDomain.builder().id(pautaId).build())
                .status(StatusEnum.ABERTO)
                .build();

        when(sessaoGateway.create(any(SessaoDomain.class))).thenReturn(expectedSessao);

        SessaoDomain result = createSessaoVotacaoUsecase.execute(pautaId, customMinutes);

        assertNotNull(result);
        assertEquals(customMinutes, result.getFim().getMinute() - result.getInicio().getMinute());
        assertEquals(StatusEnum.ABERTO, result.getStatus());
        assertEquals(pautaId, result.getPauta().getId());

        verify(sessaoGateway, times(1)).create(any(SessaoDomain.class));
    }
}
