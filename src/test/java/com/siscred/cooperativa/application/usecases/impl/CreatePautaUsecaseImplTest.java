package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.PautaGateway;
import com.siscred.cooperativa.domain.PautaDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePautaUsecaseImplTest {

    @Mock
    private PautaGateway pautaGateway;

    @InjectMocks
    private CreatePautaUsecaseImpl createPautaUsecase;

    @BeforeEach
    void setUp() {
        createPautaUsecase = new CreatePautaUsecaseImpl(pautaGateway);
    }

    @Test
    void shouldExecuteSuccessfully() {
        // Arrange
        PautaDomain pautaDomain = PautaDomain.builder().nome("Nova Pauta").build();
        PautaDomain savedPautaDomain = PautaDomain.builder().id(1L).nome("Nova Pauta").build();
        
        when(pautaGateway.create(any(PautaDomain.class))).thenReturn(savedPautaDomain);
        
        PautaDomain result = createPautaUsecase.execute(pautaDomain);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Nova Pauta", result.getNome());
        verify(pautaGateway, times(1)).create(any(PautaDomain.class));
    }
}
