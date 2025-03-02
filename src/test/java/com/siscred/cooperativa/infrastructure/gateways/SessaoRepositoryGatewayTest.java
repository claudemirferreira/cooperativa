package com.siscred.cooperativa.infrastructure.gateways;

import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.persistence.repository.SessaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessaoRepositoryGatewayTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private ModelMapper modelMapper; // Mockando o ModelMapper

    @InjectMocks
    private SessaoRepositoryGateway sessaoRepositoryGateway;

    private Sessao sessaoEntity;
    private SessaoDomain sessaoDomain;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializa objetos de teste
        sessaoEntity = new Sessao();
        sessaoEntity.setId(1L);
        sessaoEntity.setStatus(StatusEnum.ABERTO);

        sessaoDomain = new SessaoDomain();
        sessaoDomain.setId(1L);
        sessaoDomain.setStatus(StatusEnum.ABERTO);
    }

    @Test
    void testSave() {
        // Simula o comportamento do ModelMapper
        when(modelMapper.map(sessaoDomain, Sessao.class)).thenReturn(sessaoEntity);
        when(modelMapper.map(sessaoEntity, SessaoDomain.class)).thenReturn(sessaoDomain);
        when(sessaoRepository.save(sessaoEntity)).thenReturn(sessaoEntity);

        SessaoDomain result = sessaoRepositoryGateway.save(sessaoDomain);

        assertNotNull(result);
        assertEquals(sessaoDomain.getId(), result.getId());
        assertEquals(sessaoDomain.getStatus(), result.getStatus());

        verify(sessaoRepository, times(1)).save(sessaoEntity);
    }

    @Test
    void testFindById_WhenSessaoExists() {
        // Simula o comportamento do findById
        when(sessaoRepository.findById(1L)).thenReturn(java.util.Optional.of(sessaoEntity));
        when(modelMapper.map(sessaoEntity, SessaoDomain.class)).thenReturn(sessaoDomain);

        SessaoDomain result = sessaoRepositoryGateway.findById(1L);

        assertNotNull(result);
        assertEquals(sessaoDomain.getId(), result.getId());
        verify(sessaoRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_WhenSessaoNotExists() {
        // Simula o comportamento de EntityNotFoundException
        when(sessaoRepository.findById(2L)).thenReturn(java.util.Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
            sessaoRepositoryGateway.findById(2L)
        );

        assertEquals("Sessão com ID 2 não encontrada", exception.getMessage());
        verify(sessaoRepository, times(1)).findById(2L);
    }

    @Test
    void testExistSessaoAberta_WhenExists() {
        // Captura a Specification usada na chamada
        ArgumentCaptor<Specification<Sessao>> specCaptor = ArgumentCaptor.forClass(Specification.class);

        // Simula o comportamento do repository
        when(sessaoRepository.findOne(specCaptor.capture())).thenReturn(Optional.of(sessaoEntity));
        when(modelMapper.map(sessaoEntity, SessaoDomain.class)).thenReturn(sessaoDomain);

        // Chama o método que estamos testando
        boolean result = sessaoRepositoryGateway.existSessaoAberta(1L);

        // Verificações
        assertTrue(result, "A sessão deveria estar aberta");
        verify(sessaoRepository, times(1)).findOne(specCaptor.getValue());
        verify(modelMapper, times(1)).map(sessaoEntity, SessaoDomain.class);
    }

    @Test
    void testExistSessaoAberta_WhenNotExists() {
        // Simula a ausência de sessão aberta
        ArgumentCaptor<Specification<Sessao>> specCaptor = ArgumentCaptor.forClass(Specification.class);
        when(sessaoRepository.findOne(specCaptor.capture())).thenReturn(Optional.empty());

        boolean result = sessaoRepositoryGateway.existSessaoAberta(1L);

        assertFalse(result);
        verify(sessaoRepository, times(1)).findOne(specCaptor.getValue());

    }
}
