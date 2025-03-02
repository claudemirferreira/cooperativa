package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.SessaoGateway;
import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.domain.TotalVotoDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ContabilizarVotoUsecaseImplTest {

    private VotoGateway votoGateway;
    private SessaoGateway sessaoGateway;
    private ContabilizarVotoUsecaseImpl contabilizarVotoUsecase;

    @BeforeEach
    void setUp() {
        votoGateway = mock(VotoGateway.class);
        sessaoGateway = mock(SessaoGateway.class);
        contabilizarVotoUsecase = new ContabilizarVotoUsecaseImpl(votoGateway, sessaoGateway);
    }

    @Test
    void shouldExecuteAndCloseSessions() {
        // Arrange
        TotalVotoDomain totalVoto = new TotalVotoDomain(1L, "Pauta 1", 10, 5);
        SessaoDomain sessao = SessaoDomain
                .builder()
                .id(1L)
                .inicio(OffsetDateTime.now())
                .fim(OffsetDateTime.now())
                .pauta(PautaDomain.builder().id(1L).nome("Pauta").build())
                .status(StatusEnum.ABERTO)
                .build();

        when(votoGateway.countVotoSesaoAberta()).thenReturn(List.of(totalVoto));
        when(sessaoGateway.findById(1L)).thenReturn(sessao);

        // Act
        contabilizarVotoUsecase.execute();

        // Assert
        ArgumentCaptor<SessaoDomain> captor = ArgumentCaptor.forClass(SessaoDomain.class);
        verify(sessaoGateway).save(captor.capture());

        SessaoDomain updatedSessao = captor.getValue();
        assertThat(updatedSessao.getStatus()).isEqualTo(StatusEnum.FECHADO);

        verify(votoGateway, times(1)).countVotoSesaoAberta();
        verify(sessaoGateway, times(1)).findById(1L);
        verify(sessaoGateway, times(1)).save(any(SessaoDomain.class));
    }
}
