package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.application.gateways.VotoMensageriaGateway;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.exception.ExistVotoCPFException;
import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class CreateVotoUsecaseImplTest {

    @Mock
    private VotoGateway votoGateway;

    @Mock
    private VotoMensageriaGateway votoMensageriaGateway;

    @InjectMocks
    private CreateVotoUsecaseImpl createVotoUsecase;

    private VotoDomain votoDomain;
    private Voto voto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        votoDomain = VotoDomain.builder()
                .cpf("52998224725")
                .sessao(SessaoDomain.builder().id(1L).build())
                .voto(VotoEnum.SIM)
                .build();
        voto = Voto.builder()
                .cpf("52998224725")
                .sessao(Sessao.builder().id(1L).build())
                .voto(VotoEnum.SIM)
                .build();
    }

    @Test
    void shouldRegisterVoteSuccessfully() {
        // Simulando comportamento do gateway para não retornar votos existentes
        when(votoGateway.findBySessaoIdAndCpf(any(Long.class), any(String.class))).thenReturn(java.util.Collections.emptyList());

        // Chamando o método
        VotoDomain result = createVotoUsecase.execute(votoDomain.getCpf(), votoDomain.getSessao().getId(), votoDomain.getVoto());

        // Verificando se o voto foi registrado corretamente
        assertNotNull(result);
        assertEquals(votoDomain.getCpf(), result.getCpf());
        assertEquals(votoDomain.getSessao().getId(), result.getSessao().getId());
        assertEquals(votoDomain.getVoto(), result.getVoto());

        // Verificando se o método 'send' foi chamado
        verify(votoMensageriaGateway, times(1)).send(any(VotoDomain.class));
    }

    @Test
    void shouldThrowExceptionWhenVoteAlreadyExists() {
        // Simulando que já existe um voto para o CPF informado
        when(votoGateway.findBySessaoIdAndCpf(any(Long.class), any(String.class)))
                .thenReturn(java.util.Collections.singletonList(voto));

        // Chamando o método e verificando se a exceção é lançada
        ExistVotoCPFException exception = assertThrows(ExistVotoCPFException.class, () -> {
            createVotoUsecase.execute(votoDomain.getCpf(), votoDomain.getSessao().getId(), votoDomain.getVoto());
        });

        // Verificando a mensagem da exceção
        assertEquals("Já existe um voto para o CPF informado.", exception.getMessage());
    }

    @Test
    void shouldValidateCPFBeforeRegisteringVote() {
        // CPF inválido
        String invalidCpf = "00000000000";

        // Verificando se a exceção é lançada ao tentar validar um CPF inválido
        assertThrows(com.siscred.cooperativa.exception.CpfInvalidException.class, () -> {
            createVotoUsecase.execute(invalidCpf, votoDomain.getSessao().getId(), votoDomain.getVoto());
        });
    }
}
