package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.exception.ExistVotoCPFException;
import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrarVotoUsecaseImplTest {

    @Mock
    private VotoGateway votoGateway;

    @InjectMocks
    private RegistrarVotoUsecaseImpl registrarVotoUsecase;

    private VotoDomain votoDomain;
    private Voto voto;


    @BeforeEach
    void setUp() {
        SessaoDomain sessaoDomain = new SessaoDomain(); // Simulando uma SessaoDomain
        sessaoDomain.setId(1L); // Definindo um ID para a SessaoDomain

        votoDomain = VotoDomain.builder()
                .id(1L)
                .cpf("52998224725")
                .build();

        voto = Voto.builder()
                .id(1L)
                .cpf("52998224725")
                .build();
    }

    @Test
    void shouldRegisterVoteSuccessfully() {
        // Criando e inicializando a SessaoDomain para evitar o null pointer
        SessaoDomain sessaoDomain = new SessaoDomain();
        sessaoDomain.setId(1L); // Certificando-se de que a Sessao não é nula

        // Criando o VotoDomain com a Sessao inicializada
        votoDomain = VotoDomain.builder()
                .id(1L)
                .cpf("52998224725")
                .sessao(sessaoDomain) // A Sessao não será mais nula
                .voto(VotoEnum.SIM) // ou outro valor de VotoEnum conforme a lógica do teste
                .build();

        // Simulando o comportamento esperado para o gateway
        when(votoGateway.create(any(VotoDomain.class))).thenReturn(votoDomain);

        // Executando a lógica
        VotoDomain result = registrarVotoUsecase.execute(votoDomain);

        // Verificando se o método create foi chamado uma vez
        verify(votoGateway, times(1)).create(any(VotoDomain.class));

        // Validando se o retorno é o esperado
        assertNotNull(result);
        assertEquals(votoDomain, result);
    }

    @Test
    void shouldThrowExceptionWhenVoteAlreadyExists() {
        // Criando SessaoDomain e VotoDomain com dados iniciais
        SessaoDomain sessaoDomain = new SessaoDomain();
        sessaoDomain.setId(1L); // Certificando-se de que a Sessao não é nula

        VotoDomain votoDomain = VotoDomain.builder()
                .id(1L)
                .cpf("52998224725")
                .sessao(sessaoDomain) // A Sessao não será mais nula
                .voto(VotoEnum.SIM) // ou outro valor de VotoEnum conforme a lógica do teste
                .build();

        // Simulando o comportamento do gateway com CPF já existente
        when(votoGateway.findBySessaoIdAndCpf(1L, "52998224725")).thenReturn(List.of(votoDomain));

        // Esperando que a exceção seja lançada
        ExistVotoCPFException exception = assertThrows(ExistVotoCPFException.class, () -> {
            registrarVotoUsecase.execute(votoDomain);
        });

        // Verificando se a mensagem da exceção é a esperada
        assertEquals("Já existe um voto para o CPF informado.", exception.getMessage());
    }


    @Test
    void shouldHandleExceptionWhenGatewayFails() {
        // Criando o VotoDomain
        SessaoDomain sessaoDomain = new SessaoDomain();
        sessaoDomain.setId(1L); // Certificando-se de que a Sessao não é nula

        VotoDomain votoDomain = VotoDomain.builder()
                .id(1L)
                .cpf("52998224725")
                .sessao(sessaoDomain)
                .voto(VotoEnum.SIM)
                .build();

        // Configurando o mock do votoGateway para lançar a RuntimeException simulando um erro de banco de dados
        when(votoGateway.create(any(VotoDomain.class))).thenThrow(new RuntimeException("Database error"));

        // Esperando que o erro seja registrado corretamente
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            registrarVotoUsecase.execute(votoDomain);
        });

        // Verificando se a mensagem da exceção é a esperada
        assertEquals("Database error", exception.getMessage());
    }

}
