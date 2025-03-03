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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

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
                .pauta(PautaDomain.builder().id(100L).build())
                .build();
        createSessaoVotacaoResponse = new CreateSessaoVotacaoResponse(
                sessaoDomain.getId(),
                sessaoDomain.getInicio(),
                sessaoDomain.getFim(),
                sessaoDomain.getPauta(),
                sessaoDomain.getStatus()
        );
    }

    @Test
    void shouldCreateSessaoVotacaoWithDefaultMinutes() {
        Long pautaId = 100L;
        Integer minutos = 1; // Default

        when(createSessaoUsecase.execute(pautaId, minutos)).thenReturn(sessaoDomain);
        when(modelMapper.map(sessaoDomain, CreateSessaoVotacaoResponse.class)).thenReturn(createSessaoVotacaoResponse);

        ResponseEntity<CreateSessaoVotacaoResponse> response = sessaoController.create(pautaId, minutos);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(URI.create("/sessao/v1" + sessaoDomain.getId()), response.getHeaders().getLocation());
        assertNotNull(response.getBody());
        assertEquals(sessaoDomain.getId(), response.getBody().id());
        assertEquals(sessaoDomain.getInicio(), response.getBody().inicio());
        assertEquals(sessaoDomain.getFim(), response.getBody().fim());
        assertEquals(sessaoDomain.getPauta(), response.getBody().pauta());
        assertEquals(sessaoDomain.getStatus(), response.getBody().status());
    }

    @Test
    void shouldCreateSessaoVotacaoWithCustomMinutes() {
        Long pautaId = 200L;
        Integer minutos = 10;

        SessaoDomain sessaoPersonalizada = SessaoDomain.builder()
                .id(2L)
                .pauta(PautaDomain.builder().id(100L).build())
                .fim(sessaoDomain.getFim())
                .inicio(sessaoDomain.getInicio())
                .build();

        CreateSessaoVotacaoResponse responsePersonalizada = new CreateSessaoVotacaoResponse(
                sessaoPersonalizada.getId(),
                sessaoPersonalizada.getInicio(),
                sessaoPersonalizada.getFim(),
                sessaoPersonalizada.getPauta(),
                sessaoPersonalizada.getStatus()
        );

        when(createSessaoUsecase.execute(pautaId, minutos)).thenReturn(sessaoPersonalizada);
        when(modelMapper.map(sessaoPersonalizada, CreateSessaoVotacaoResponse.class)).thenReturn(responsePersonalizada);

        ResponseEntity<CreateSessaoVotacaoResponse> response = sessaoController.create(pautaId, minutos);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(URI.create("/sessao/v1" + sessaoPersonalizada.getId()), response.getHeaders().getLocation());
        assertNotNull(response.getBody());
        assertEquals(sessaoPersonalizada.getId(), response.getBody().id());
        assertEquals(sessaoPersonalizada.getInicio(), response.getBody().inicio());
        assertEquals(sessaoPersonalizada.getFim(), response.getBody().fim());
        assertEquals(sessaoPersonalizada.getPauta(), response.getBody().pauta());
        assertEquals(sessaoPersonalizada.getStatus(), response.getBody().status());
    }
}
