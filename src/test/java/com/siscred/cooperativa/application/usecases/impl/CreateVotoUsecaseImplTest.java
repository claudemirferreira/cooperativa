package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.exception.ExistVotoCPFException;
import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateVotoUsecaseImplTest {

    @Mock
    private VotoGateway votoGateway;

    @InjectMocks
    private CreateVotoUsecaseImpl createVotoUsecase;

    private String validCpf;
    private Long sessaoId;
    private VotoEnum voto;
    private VotoDomain votoDomain;
    private Voto votoEntity;

    @BeforeEach
    void setUp() {
        validCpf = "60735090220";
        sessaoId = 10L;
        voto = VotoEnum.SIM;

        votoDomain = VotoDomain.builder()
                .sessao(SessaoDomain.builder().id(sessaoId).build())
                .cpf(validCpf)
                .voto(voto)
                .build();

        votoEntity = Voto.builder()
                .sessao(Sessao.builder().id(sessaoId).build())
                .cpf(validCpf)
                .voto(voto)
                .build();
    }

    @Test
    void shouldCreateVotoSuccessfully() {
        // Arrange
        when(votoGateway.findBySessaoIdAndCpf(sessaoId, validCpf)).thenReturn(Collections.emptyList());
        when(votoGateway.create(any(VotoDomain.class))).thenReturn(votoDomain);

        // Act
        VotoDomain result = createVotoUsecase.execute(validCpf, sessaoId, voto);

        // Assert
        assertNotNull(result);
        assertEquals(validCpf, result.getCpf());
        assertEquals(sessaoId, result.getSessao().getId());
        assertEquals(voto, result.getVoto());

        // Verify
        verify(votoGateway, times(1)).findBySessaoIdAndCpf(sessaoId, validCpf);
        verify(votoGateway, times(1)).create(any(VotoDomain.class));
    }

    @Test
    void shouldThrowExceptionWhenVotoAlreadyExists() {
        // Arrange
        when(votoGateway.findBySessaoIdAndCpf(sessaoId, validCpf)).thenReturn(Collections.<Voto>singletonList(votoEntity));

        // Act & Assert
        ExistVotoCPFException exception = assertThrows(
                ExistVotoCPFException.class,
                () -> createVotoUsecase.execute(validCpf, sessaoId, voto)
        );

        assertEquals("Já existe um voto para o CPF informado.", exception.getMessage());

        // Verify
        verify(votoGateway, times(1)).findBySessaoIdAndCpf(sessaoId, validCpf);
        verify(votoGateway, never()).create(any(VotoDomain.class));
    }
}
