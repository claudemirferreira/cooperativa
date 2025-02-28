package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import com.siscred.cooperativa.infrastructure.persistence.entity.Pauta;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.repository.SessaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.OffsetDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SessaoVotoRepositoryGatewayTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SessaoRepositoryGateway gateway;

    private SessaoDomain sessaoDomain;
    private Sessao sessaoVotacaoEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sessaoDomain = SessaoDomain
                .builder()
                .status(StatusEnum.ABERTO)
                .inicio(OffsetDateTime.now())
                .fim(OffsetDateTime.now())
                .pauta(PautaDomain.builder().id(1L).build())
                .build();

        sessaoVotacaoEntity = Sessao
                .builder()
                .pauta(Pauta.builder().id(1L).build())
                .inicio(OffsetDateTime.now())
                .fim(OffsetDateTime.now())
                .status(StatusEnum.ABERTO)
                .build();

        when(modelMapper.map(sessaoDomain, Sessao.class)).thenReturn(sessaoVotacaoEntity);
        when(modelMapper.map(sessaoVotacaoEntity, SessaoDomain.class)).thenReturn(sessaoDomain);
    }

    @Test
    void testCreate() {
        SessaoDomain result = gateway.create(sessaoDomain);

        verify(sessaoRepository, times(1)).save(sessaoVotacaoEntity);

        assertNotNull(result);
        assertEquals(sessaoDomain, result);

        verify(modelMapper, times(1)).map(sessaoDomain, Sessao.class);
        verify(modelMapper, times(1)).map(sessaoVotacaoEntity, SessaoDomain.class);
    }
}
