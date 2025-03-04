package com.siscred.cooperativa.infrastructure.controller;

import com.siscred.cooperativa.application.usecases.CreateSessaoUsecase;
import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.infrastructure.controller.dto.response.CreateSessaoVotacaoResponse;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessaoControllerTest {

    @Mock
    private CreateSessaoUsecase createSessaoUsecase;

    @InjectMocks
    private SessaoController sessaoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnCreatedResponse() {
        // Arrange
        Long pautaId = 1L;
        Integer tempoExpiracao = 5;
        PautaDomain pauta = new PautaDomain(pautaId, "Pauta Teste");

        SessaoDomain sessaoDomain = new SessaoDomain(1L, LocalDateTime.now(), LocalDateTime.now().plusMinutes(tempoExpiracao), pauta, StatusEnum.ABERTO);

        when(createSessaoUsecase.execute(pautaId, tempoExpiracao)).thenReturn(sessaoDomain);

        // Act
        ResponseEntity<CreateSessaoVotacaoResponse> response = sessaoController.create(pautaId, tempoExpiracao);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sessaoDomain.getId(), response.getBody().id());
        assertEquals(sessaoDomain.getStatus(), response.getBody().status());

        verify(createSessaoUsecase, times(1)).execute(pautaId, tempoExpiracao);
    }

}
