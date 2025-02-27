package com.siscred.cooperativa.infrastructure.controller;

import com.siscred.cooperativa.application.usecases.CreatePautaUsecase;
import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.infrastructure.controller.dto.request.PautaRequest;
import com.siscred.cooperativa.infrastructure.controller.dto.response.PautaResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PautaControllerTest {

    @Mock
    private CreatePautaUsecase createPautaUsecase;

    @InjectMocks
    private PautaController pautaController;

    @BeforeEach
    void setUp() {
        pautaController = new PautaController(createPautaUsecase);
    }

    @Test
    void shouldCreatePautaSuccessfully() {
        // Arrange
        PautaRequest request = new PautaRequest("Nova Pauta");
        PautaDomain createdPauta = PautaDomain.builder().id(1L).nome("Nova Pauta").build();
        
        when(createPautaUsecase.execute(any(PautaDomain.class))).thenReturn(createdPauta);
        
        // Act
        ResponseEntity<PautaResponse> response = pautaController.createCard(request);
        
        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Nova Pauta", response.getBody().getNome());
        verify(createPautaUsecase, times(1)).execute(any(PautaDomain.class));
    }
}