package com.siscred.cooperativa.infrastructure.controller;

import com.siscred.cooperativa.application.usecases.CreateVotoUsecase;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VotoControllerTest {

    @InjectMocks
    private VotoController votoController;

    @Mock
    private CreateVotoUsecase createVotoUsecase;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(votoController).build();

    }

    @Test
    void create_shouldReturnCreatedVotoResponse() throws Exception {
        // Arrange
        String cpf = "12345678900";
        Long sessaoId = 1L;
        VotoEnum voto = VotoEnum.SIM;

        VotoDomain votoDomain = VotoDomain.builder()
                .voto(VotoEnum.SIM)
                .sessao(SessaoDomain.builder().id(1L).build())
                .id(12345678900L)
                .cpf("12345678900")
                .build();
        // Mock o comportamento do caso de uso
        when(createVotoUsecase.execute(cpf, sessaoId, voto)).thenReturn(votoDomain);

        // Act & Assert
        mockMvc.perform(post("/voto/v1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cpf\": \"12345678900\", \"sessaoId\": 1, \"voto\": \"SIM\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/voto/v1/12345678900"))
                .andExpect(jsonPath("$.cpf").value(cpf))
                .andExpect(jsonPath("$.voto").value(voto.toString()))
                .andExpect(jsonPath("$.sessaoId").value(sessaoId));

        // Verificar se o método execute foi chamado
        verify(createVotoUsecase, times(1)).execute(cpf, sessaoId, voto);
    }

}
