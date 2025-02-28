package com.siscred.cooperativa.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siscred.cooperativa.application.usecases.CreateVotoUsecase;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.exception.CpfInvalidException;
import com.siscred.cooperativa.infrastructure.controller.dto.request.CreateVotoRequest;
import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VotoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateVotoUsecase createVotoUsecase;

    @InjectMocks
    private VotoController votoController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private CreateVotoRequest request;
    private VotoDomain votoDomain;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(votoController).build();

        request = new CreateVotoRequest("60735090220", 10L, VotoEnum.SIM);

        votoDomain = VotoDomain.builder()
                .cpf("60735090220")
                .sessao(SessaoDomain.builder().id(10L).build())
                .voto(VotoEnum.SIM)
                .build();
    }

    @Test
    void shouldCreateVotoSuccessfully() throws Exception {
        // Arrange
        when(createVotoUsecase.execute(request.getCpf(), request.getSessaoId(), request.getVoto()))
                .thenReturn(votoDomain);

        // Act & Assert
        mockMvc.perform(post("/voto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/voto/60735090220"))
                .andExpect(jsonPath("$.cpf").value("60735090220"))
                .andExpect(jsonPath("$.sessaoId").value(10))
                .andExpect(jsonPath("$.voto").value("SIM"));
    }

}
