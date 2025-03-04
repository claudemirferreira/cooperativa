package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.entity.Pauta;
import com.siscred.cooperativa.infrastructure.persistence.repository.PautaRepository;
import com.siscred.cooperativa.infrastructure.persistence.repository.SessaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessaoRepositoryGatewayTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SessaoRepositoryGateway sessaoRepositoryGateway;

    private SessaoDomain sessaoDomain;
    private Sessao sessaoEntity;
    private Pauta pauta;
    private PautaDomain pautaDomain;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        pautaDomain = new PautaDomain();
        pautaDomain.setId(1L);

        pauta = new Pauta();
        pauta.setId(1L);

        sessaoDomain = new SessaoDomain();
        sessaoDomain.setPauta(pautaDomain);

        sessaoEntity = new Sessao();
        sessaoEntity.setPauta(pauta);

        when(pautaRepository.findById(1L)).thenReturn(java.util.Optional.of(pauta));
        when(modelMapper.map(sessaoDomain, Sessao.class)).thenReturn(sessaoEntity);
        when(sessaoRepository.save(sessaoEntity)).thenReturn(sessaoEntity);
        when(modelMapper.map(sessaoEntity, SessaoDomain.class)).thenReturn(sessaoDomain);
    }

    @Test
    void testSave() {
        SessaoDomain result = sessaoRepositoryGateway.save(sessaoDomain);

        verify(sessaoRepository).save(sessaoEntity);
        assertNotNull(result);
        assertEquals(sessaoDomain, result);

        verify(pautaRepository).findById(1L);
    }

    @Test
    void testFindById_Success() {
        when(sessaoRepository.findById(1L)).thenReturn(java.util.Optional.of(sessaoEntity));

        SessaoDomain result = sessaoRepositoryGateway.findById(1L);

        verify(sessaoRepository).findById(1L);

        assertNotNull(result);
        assertEquals(sessaoDomain, result);
    }

    @Test
    void testFindById_NotFound() {
        when(sessaoRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            sessaoRepositoryGateway.findById(1L);
        });

        assertEquals("Sessão com ID 1 não encontrada", exception.getMessage());
    }

    @Test
    void testExistSessaoAberta() {
        when(sessaoRepository.findOne(any(Specification.class))).thenReturn(java.util.Optional.of(sessaoEntity));

        Boolean result = sessaoRepositoryGateway.existSessaoAberta(1L);

        verify(sessaoRepository).findOne(any(Specification.class));

        assertTrue(result);
    }
}
