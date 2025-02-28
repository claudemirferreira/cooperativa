package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.SessaoVotacaoGateway;
import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.domain.SessaoVotacaoDomain;
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
class CreateSessaoVotacaoUsecaseImplTest {

    @InjectMocks
    private CreateSessaoVotacaoUsecaseImpl createSessaoVotacaoUsecase;

    @Mock
    private SessaoVotacaoGateway sessaoVotacaoGateway;

    private Long pautaId;
    private OffsetDateTime start;

    @BeforeEach
    void setUp() {
        pautaId = 1L;
        start = OffsetDateTime.now();
    }

    @Test
    void shouldCreateSessaoVotacaoWithDefaultTime() {
        SessaoVotacaoDomain expectedSessao = SessaoVotacaoDomain.builder()
                .inicio(start)
                .fim(start.plusMinutes(1)) // Tempo padrão
                .pauta(PautaDomain.builder().id(pautaId).build())
                .status(StatusEnum.ABERTO)
                .build();

        when(sessaoVotacaoGateway.create(any(SessaoVotacaoDomain.class))).thenReturn(expectedSessao);

        SessaoVotacaoDomain result = createSessaoVotacaoUsecase.execute(pautaId, null);

        assertNotNull(result);
        assertEquals(1, result.getFim().getMinute() - result.getInicio().getMinute()); // 1 minuto padrão
        assertEquals(StatusEnum.ABERTO, result.getStatus());
        assertEquals(pautaId, result.getPauta().getId());

        verify(sessaoVotacaoGateway, times(1)).create(any(SessaoVotacaoDomain.class));
    }

    @Test
    void shouldCreateSessaoVotacaoWithCustomTime() {
        int customMinutes = 5;

        SessaoVotacaoDomain expectedSessao = SessaoVotacaoDomain.builder()
                .inicio(start)
                .fim(start.plusMinutes(customMinutes))
                .pauta(PautaDomain.builder().id(pautaId).build())
                .status(StatusEnum.ABERTO)
                .build();

        when(sessaoVotacaoGateway.create(any(SessaoVotacaoDomain.class))).thenReturn(expectedSessao);

        SessaoVotacaoDomain result = createSessaoVotacaoUsecase.execute(pautaId, customMinutes);

        assertNotNull(result);
        assertEquals(customMinutes, result.getFim().getMinute() - result.getInicio().getMinute());
        assertEquals(StatusEnum.ABERTO, result.getStatus());
        assertEquals(pautaId, result.getPauta().getId());

        verify(sessaoVotacaoGateway, times(1)).create(any(SessaoVotacaoDomain.class));
    }
}
