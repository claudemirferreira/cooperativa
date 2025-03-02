package com.siscred.cooperativa.infrastructure.persistence.specification;

import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VotoSpecificationTest {

    @Mock
    private Root<Voto> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private Path<Object> path;

    @Mock
    private Path<Object> sessaoPath;

    @Mock
    private Path<Object> idPath;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(root.get("cpf")).thenReturn(path);
        when(root.get("sessao")).thenReturn(sessaoPath);
        when(sessaoPath.get("id")).thenReturn(idPath);
    }

    @Test
    void shouldReturnPredicateWhenFilteringBySessaoId() {
        // Given
        Long sessaoId = 1L;
        Specification<Voto> spec = VotoSpecification.bySessaoId(sessaoId);
        Predicate predicate = mock(Predicate.class);

        when(criteriaBuilder.equal(idPath, sessaoId)).thenReturn(predicate);

        // When
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        // Then
        assertNotNull(result);
        verify(criteriaBuilder, times(1)).equal(idPath, sessaoId);
    }

    @Test
    void shouldReturnNullWhenFilteringByNullSessaoId() {
        // Given
        Specification<Voto> spec = VotoSpecification.bySessaoId(null);

        // When
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        // Then
        assertNull(result);
    }

    @Test
    void shouldReturnPredicateWhenFilteringByCpf() {
        // Given
        String cpf = "12345678900";
        Specification<Voto> spec = VotoSpecification.byCpf(cpf);
        Predicate predicate = mock(Predicate.class);

        when(criteriaBuilder.equal(path, cpf)).thenReturn(predicate);

        // When
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        // Then
        assertNotNull(result);
        verify(criteriaBuilder, times(1)).equal(path, cpf);
    }

    @Test
    void shouldReturnNullWhenFilteringByNullCpf() {
        // Given
        Specification<Voto> spec = VotoSpecification.byCpf(null);

        // When
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        // Then
        assertNull(result);
    }

    @Test
    void shouldReturnNullWhenFilteringByEmptyCpf() {
        // Given
        Specification<Voto> spec = VotoSpecification.byCpf("");

        // When
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        // Then
        assertNull(result);
    }

    @Test
    void shouldCombineSpecificationsForFilterBySessaoAndCpf() {
        // Given
        Long sessaoId = 1L;
        String cpf = "12345678900";
        Specification<Voto> spec = VotoSpecification.filterBySessaoAndCpf(sessaoId, cpf);

        Predicate sessaoPredicate = mock(Predicate.class);
        Predicate cpfPredicate = mock(Predicate.class);

        when(criteriaBuilder.equal(idPath, sessaoId)).thenReturn(sessaoPredicate);
        when(criteriaBuilder.equal(path, cpf)).thenReturn(cpfPredicate);
        when(criteriaBuilder.and(sessaoPredicate, cpfPredicate)).thenReturn(mock(Predicate.class));

        // When
        Predicate result = spec.toPredicate(root, query, criteriaBuilder);

        // Then
        assertNotNull(result);
        verify(criteriaBuilder, times(1)).equal(idPath, sessaoId);
        verify(criteriaBuilder, times(1)).equal(path, cpf);
        verify(criteriaBuilder, times(1)).and(sessaoPredicate, cpfPredicate);
    }
}
