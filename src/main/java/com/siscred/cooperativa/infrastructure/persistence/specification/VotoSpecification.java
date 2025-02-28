package com.siscred.cooperativa.infrastructure.persistence.specification;

import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import org.springframework.data.jpa.domain.Specification;

public class VotoSpecification {

    public static Specification<Voto> bySessaoId(Long sessaoId) {
        return (root, query, criteriaBuilder) -> 
            sessaoId == null ? null : criteriaBuilder.equal(root.get("sessao").get("id"), sessaoId);
    }

    public static Specification<Voto> byCpf(String cpf) {
        return (root, query, criteriaBuilder) -> 
            (cpf == null || cpf.isEmpty()) ? null : criteriaBuilder.equal(root.get("cpf"), cpf);
    }

    public static Specification<Voto> filterBySessaoAndCpf(Long sessaoId, String cpf) {
        return Specification.where(bySessaoId(sessaoId)).and(byCpf(cpf));
    }
}
