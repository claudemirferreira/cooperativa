package com.siscred.cooperativa.infrastructure.controller;

import com.siscred.cooperativa.application.usecases.CreateSessaoUsecase;
import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.domain.SessaoDomain;
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
class SessaoVotoControllerTest {

    @InjectMocks
    private SessaoController sessaoController;

    @Mock
    private CreateSessaoUsecase createSessaoUsecase;

    @Mock
    private ModelMapper modelMapper;

    private SessaoDomain sessaoDomain;
    private CreateSessaoVotacaoResponse createSessaoVotacaoResponse;

    @BeforeEach
    void setUp() {
        sessaoDomain = SessaoDomain.builder()
                .id(1L)
                .pauta( PautaDomain.builder().id(100L).build() )
                .build();

        createSessaoVotacaoResponse = CreateSessaoVotacaoResponse.builder()
                .id(sessaoDomain.getId())
                .pauta(sessaoDomain.getPauta())
                .fim(sessaoDomain.getFim())
                .inicio(sessaoDomain.getInicio())
                .build();
    }

    @Test
    void shouldCreateSessaoVotacaoWithDefaultMinutes() {
        Long pautaId = 100L;
        Integer minutos = 1; // Default

        when(createSessaoUsecase.execute(pautaId, minutos)).thenReturn(sessaoDomain);
        when(modelMapper.map(sessaoDomain, CreateSessaoVotacaoResponse.class)).thenReturn(createSessaoVotacaoResponse);

        ResponseEntity<CreateSessaoVotacaoResponse> response = sessaoController.create(pautaId, minutos);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(URI.create("/sessao-votacao/" + sessaoDomain.getId()), response.getHeaders().getLocation());
        assertNotNull(response.getBody());
        assertEquals(sessaoDomain.getId(), response.getBody().getId());
    }

    @Test
    void shouldCreateSessaoVotacaoWithCustomMinutes() {
        Long pautaId = 200L;
        Integer minutos = 10;

        SessaoDomain sessaoPersonalizada = SessaoDomain.builder()
                .id(2L)
                .pauta( PautaDomain.builder().id(100L).build() )
                .fim(sessaoDomain.getFim())
                .inicio(sessaoDomain.getInicio())
                .build();

        CreateSessaoVotacaoResponse responsePersonalizada = CreateSessaoVotacaoResponse.builder()
                .id(sessaoPersonalizada.getId())
                .pauta( PautaDomain.builder().id(100L).build() )
                .fim(sessaoDomain.getFim())
                .inicio(sessaoDomain.getInicio())
                .build();

        when(createSessaoUsecase.execute(pautaId, minutos)).thenReturn(sessaoPersonalizada);
        when(modelMapper.map(sessaoPersonalizada, CreateSessaoVotacaoResponse.class)).thenReturn(responsePersonalizada);

        ResponseEntity<CreateSessaoVotacaoResponse> response = sessaoController.create(pautaId, minutos);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(URI.create("/sessao-votacao/" + sessaoPersonalizada.getId()), response.getHeaders().getLocation());
        assertNotNull(response.getBody());
        assertEquals(sessaoPersonalizada.getId(), response.getBody().getId());
    }
}
