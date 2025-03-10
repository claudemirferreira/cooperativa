package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.SessaoGateway;
import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.domain.TotalVotoDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import com.siscred.cooperativa.infrastructure.gateways.kafka.producer.ContabilizarVotoKafkaProducerGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ContabilizarVotoUsecaseImplTest {

    @Mock
    private VotoGateway votoGateway;

    @Mock
    private SessaoGateway sessaoGateway;

    @Mock
    private ContabilizarVotoKafkaProducerGateway contabilizarVotoKafkaProducerGateway;

    @InjectMocks
    private ContabilizarVotoUsecaseImpl contabilizarVotoUsecase;

    @Test
    void testExecute() {
        // Arrange: Criação de objetos de teste
        TotalVotoDomain totalVotoDomain = createTotalVotoDomain(1L, "Pauta de Exemplo", 10, 5);
        
        // Simulando o retorno do countVotoSesaoAberta()
        when(votoGateway.countVotoSesaoAberta()).thenReturn(List.of(totalVotoDomain));

        // Simulando o retorno de findById() para a sessão
        SessaoDomain sessaoDomain = createSessaoDomain(1L, StatusEnum.ABERTO);
        when(sessaoGateway.findById(1L)).thenReturn(sessaoDomain);

        contabilizarVotoUsecase.execute();

        // Assert: Verificações de interações e alterações esperadas

        // Verifica se o status da sessão foi alterado para FECHADO
        verify(sessaoGateway, times(1)).save(sessaoDomain);
        assertEquals(StatusEnum.FECHADO, sessaoDomain.getStatus(), "O status da sessão não foi alterado para FECHADO");

        verify(contabilizarVotoKafkaProducerGateway, times(1)).send(totalVotoDomain);

        verify(votoGateway, times(1)).countVotoSesaoAberta();
    }

    private TotalVotoDomain createTotalVotoDomain(Long sessaoId, String pauta, int totalSim, int totalNao) {
        return TotalVotoDomain.builder()
                .sessaoId(sessaoId)
                .totalSim(totalSim)
                .totalNao(totalNao)
                .pauta(pauta)
                .build();
    }

    private SessaoDomain createSessaoDomain(Long id, StatusEnum status) {
        SessaoDomain sessaoDomain = new SessaoDomain();
        sessaoDomain.setId(id);
        sessaoDomain.setStatus(status);
        return sessaoDomain;
    }
}
