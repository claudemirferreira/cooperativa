package com.siscred.cooperativa.infrastructure.persistence.specification;

import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import static org.mockito.Mockito.*;

class SessaoSpecificationTest {

    @Test
    void testBySessaoId() {
        // Arrange
        Long sessaoId = 1L;
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Sessao> criteriaQuery = mock(CriteriaQuery.class);
        Root<Sessao> root = mock(Root.class);

        // Cria a especificação
        Specification<Sessao> specification = SessaoSpecification.bySessaoId(sessaoId);

        // Simula o comportamento da especificação
        specification.toPredicate(root, criteriaQuery, criteriaBuilder);

        verify(criteriaBuilder, times(1)).equal(root.get("id"), sessaoId);
    }

    @Test
    void testByStatus() {
        // Arrange
        StatusEnum status = StatusEnum.ABERTO;
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Sessao> criteriaQuery = mock(CriteriaQuery.class);
        Root<Sessao> root = mock(Root.class);

        // Cria a especificação
        Specification<Sessao> specification = SessaoSpecification.byStatus(status);

        // Simula o comportamento da especificação
        specification.toPredicate(root, criteriaQuery, criteriaBuilder);

        verify(criteriaBuilder, times(1)).equal(root.get("status"), status);
    }

    @Test
    void testFilterBySessaoIdAndStatus() {
        // Arrange
        Long sessaoId = 1L;
        StatusEnum status = StatusEnum.ABERTO;
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Sessao> criteriaQuery = mock(CriteriaQuery.class);
        Root<Sessao> root = mock(Root.class);

        // Cria a especificação combinada
        Specification<Sessao> specification = SessaoSpecification.filterBySessaoIdAndStatus(sessaoId, status);

        // Simula o comportamento da especificação
        specification.toPredicate(root, criteriaQuery, criteriaBuilder);

        // Verifica se os métodos criteriaBuilder.equal foram chamados corretamente
        verify(criteriaBuilder, times(1)).equal(root.get("id"), sessaoId);
        verify(criteriaBuilder, times(1)).equal(root.get("status"), status);
    }
}
