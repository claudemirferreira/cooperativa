package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.SessaoGateway;
import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.application.gateways.VotoMensageriaGateway;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.exception.ExistVotoCPFException;
import com.siscred.cooperativa.exception.NotExistSessaoAbertaException;
import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateVotoUsecaseImplTest {

    @Mock
    private VotoGateway votoGateway;

    @Mock
    private VotoMensageriaGateway votoMensageriaGateway;

    @Mock
    private SessaoGateway sessaoGateway;

    @InjectMocks
    private CreateVotoUsecaseImpl createVotoUsecase;

    private String cpf = "60735090220";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldCreateVotoSuccessfully() {
        Long sessaoId = 1L;
        VotoEnum voto = VotoEnum.SIM;

        // Simula que a sessão está aberta
        when(sessaoGateway.existSessaoAberta(sessaoId)).thenReturn(true);
        // Simula que não há voto para o CPF na sessão
        when(votoGateway.findBySessaoIdAndCpf(sessaoId, cpf)).thenReturn(java.util.Collections.emptyList());

        VotoDomain result = createVotoUsecase.execute(cpf, sessaoId, voto);

        assertNotNull(result);
        assertEquals(cpf, result.getCpf());
        assertEquals(sessaoId, result.getSessao().getId());
        assertEquals(voto, result.getVoto());

        // Verifica se a mensagem foi enviada para o gateway
        verify(votoMensageriaGateway, times(1)).send(result);
    }

    @Test
    void execute_ShouldThrowExistVotoCPFException_WhenVotoExists() {
        Long sessaoId = 1L;
        VotoEnum voto = VotoEnum.SIM;

        // Simula que a sessão está aberta
        when(sessaoGateway.existSessaoAberta(sessaoId)).thenReturn(true);
        // Simula que já existe um voto para o CPF
        when(votoGateway.findBySessaoIdAndCpf(sessaoId, cpf)).thenReturn(java.util.Collections.singletonList(new VotoDomain()));

        ExistVotoCPFException exception = assertThrows(ExistVotoCPFException.class, () -> {
            createVotoUsecase.execute(cpf, sessaoId, voto);
        });

        assertEquals("Já existe um voto para o CPF informado.", exception.getMessage());
    }

    @Test
    void execute_ShouldThrowNotExistSessaoAbertaException_WhenSessaoIsClosed() {
        Long sessaoId = 1L;
        VotoEnum voto = VotoEnum.SIM;

        // Simula que a sessão está fechada
        when(sessaoGateway.existSessaoAberta(sessaoId)).thenReturn(false);

        NotExistSessaoAbertaException exception = assertThrows(NotExistSessaoAbertaException.class, () -> {
            createVotoUsecase.execute(cpf, sessaoId, voto);
        });

        assertEquals("Não existe sessão aberta para o id 1", exception.getMessage());
    }
}
