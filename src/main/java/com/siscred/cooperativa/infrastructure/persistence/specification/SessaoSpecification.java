package com.siscred.cooperativa.infrastructure.persistence.specification;

import com.siscred.cooperativa.infrastructure.persistence.entity.Sessao;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import org.springframework.data.jpa.domain.Specification;

public class SessaoSpecification {

    private SessaoSpecification() {}

    public static Specification<Sessao> bySessaoId(Long sessaoId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), sessaoId);
    }

    public static Specification<Sessao> byStatus(StatusEnum status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Sessao> filterBySessaoIdAndStatus(Long sessaoId, StatusEnum status) {
        return Specification.where(bySessaoId(sessaoId)).and(byStatus(status));
    }
}
