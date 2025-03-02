package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.infrastructure.persistence.entity.Pauta;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.repository.SessaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessaoRepositoryGatewayTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SessaoRepositoryGateway sessaoRepositoryGateway;

    private SessaoDomain sessaoDomain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sessaoDomain = new SessaoDomain();
        sessaoDomain.setId(1L);
        PautaDomain pauta = new PautaDomain();
        pauta.setId(1L);
        sessaoDomain.setPauta(pauta);
    }

    @Test
    void testSave() {
        // Arrange
        Sessao sessaoEntity = new Sessao();
        sessaoEntity.setId(1L);
        sessaoEntity.setPauta(new Pauta());

        // Configurando o comportamento do ModelMapper
        when(modelMapper.map(sessaoDomain, Sessao.class)).thenReturn(sessaoEntity);
        when(sessaoRepository.save(sessaoEntity)).thenReturn(sessaoEntity);
        when(modelMapper.map(sessaoEntity, SessaoDomain.class)).thenReturn(sessaoDomain);

        // Act
        SessaoDomain savedSessao = sessaoRepositoryGateway.save(sessaoDomain);

        // Assert
        assertNotNull(savedSessao);
        assertEquals(1L, savedSessao.getId());
        verify(sessaoRepository, times(1)).save(sessaoEntity);
        verify(modelMapper, times(1)).map(sessaoDomain, Sessao.class);
        verify(modelMapper, times(1)).map(sessaoEntity, SessaoDomain.class);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        Sessao sessaoEntity = new Sessao();
        sessaoEntity.setId(1L);

        when(sessaoRepository.findById(1L)).thenReturn(java.util.Optional.of(sessaoEntity));
        when(modelMapper.map(sessaoEntity, SessaoDomain.class)).thenReturn(sessaoDomain);

        // Act
        SessaoDomain foundSessao = sessaoRepositoryGateway.findById(1L);

        // Assert
        assertNotNull(foundSessao);
        assertEquals(1L, foundSessao.getId());
        verify(sessaoRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(sessaoRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            sessaoRepositoryGateway.findById(999L);
        });
        assertEquals("Sessão com ID 999 não encontrada", thrown.getMessage());
    }

}
