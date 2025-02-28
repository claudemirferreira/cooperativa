package com.siscred.cooperativa.infrastructure.controller;

import com.siscred.cooperativa.application.usecases.CreateSessaoVotacaoUsecase;
import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.domain.SessaoVotacaoDomain;
import com.siscred.cooperativa.infrastructure.controller.dto.response.CreateSessaoVotacaoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessaoVotacaoControllerTest {

    @InjectMocks
    private SessaoVotacaoController sessaoVotacaoController;

    @Mock
    private CreateSessaoVotacaoUsecase createSessaoVotacaoUsecase;

    @Mock
    private ModelMapper modelMapper;

    private SessaoVotacaoDomain sessaoVotacaoDomain;
    private CreateSessaoVotacaoResponse createSessaoVotacaoResponse;

    @BeforeEach
    void setUp() {
        sessaoVotacaoDomain = SessaoVotacaoDomain.builder()
                .id(1L)
                .pauta( PautaDomain.builder().id(100L).build() )
                .build();

        createSessaoVotacaoResponse = CreateSessaoVotacaoResponse.builder()
                .id(sessaoVotacaoDomain.getId())
                .pauta(sessaoVotacaoDomain.getPauta())
                .fim(sessaoVotacaoDomain.getFim())
                .inicio(sessaoVotacaoDomain.getInicio())
                .build();
    }

    @Test
    void shouldCreateSessaoVotacaoWithDefaultMinutes() {
        Long pautaId = 100L;
        Integer minutos = 1; // Default

        when(createSessaoVotacaoUsecase.execute(pautaId, minutos)).thenReturn(sessaoVotacaoDomain);
        when(modelMapper.map(sessaoVotacaoDomain, CreateSessaoVotacaoResponse.class)).thenReturn(createSessaoVotacaoResponse);

        ResponseEntity<CreateSessaoVotacaoResponse> response = sessaoVotacaoController.createSessaoVotacao(pautaId, minutos);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(URI.create("/sessao-votacao/" + sessaoVotacaoDomain.getId()), response.getHeaders().getLocation());
        assertNotNull(response.getBody());
        assertEquals(sessaoVotacaoDomain.getId(), response.getBody().getId());
    }

    @Test
    void shouldCreateSessaoVotacaoWithCustomMinutes() {
        Long pautaId = 200L;
        Integer minutos = 10;

        SessaoVotacaoDomain sessaoPersonalizada = SessaoVotacaoDomain.builder()
                .id(2L)
                .pauta( PautaDomain.builder().id(100L).build() )
                .fim(sessaoVotacaoDomain.getFim())
                .inicio(sessaoVotacaoDomain.getInicio())
                .build();

        CreateSessaoVotacaoResponse responsePersonalizada = CreateSessaoVotacaoResponse.builder()
                .id(sessaoPersonalizada.getId())
                .pauta( PautaDomain.builder().id(100L).build() )
                .fim(sessaoVotacaoDomain.getFim())
                .inicio(sessaoVotacaoDomain.getInicio())
                .build();

        when(createSessaoVotacaoUsecase.execute(pautaId, minutos)).thenReturn(sessaoPersonalizada);
        when(modelMapper.map(sessaoPersonalizada, CreateSessaoVotacaoResponse.class)).thenReturn(responsePersonalizada);

        ResponseEntity<CreateSessaoVotacaoResponse> response = sessaoVotacaoController.createSessaoVotacao(pautaId, minutos);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(URI.create("/sessao-votacao/" + sessaoPersonalizada.getId()), response.getHeaders().getLocation());
        assertNotNull(response.getBody());
        assertEquals(sessaoPersonalizada.getId(), response.getBody().getId());
    }
}
