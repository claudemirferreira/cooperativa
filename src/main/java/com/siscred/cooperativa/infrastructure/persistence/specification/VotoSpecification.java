package com.siscred.cooperativa.infrastructure.persistence.specification;

import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Selection;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VotoSpecification {

    private VotoSpecification(){}

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

    public static Specification<Voto> countVotoBySessaoId(Long sessaoId) {
        return (root, query, builder) -> {
            // Criando uma lista de seleções para SELECT COUNT(voto), voto
            List<Selection<?>> selections = new ArrayList<>();
            selections.add(builder.count(root.get("voto")).alias("total"));
            selections.add(root.get("voto"));

            // Definindo a projeção da consulta
            query.multiselect(selections);

            // Aplicando a condição WHERE v.sessao_id = :sessaoId
            Predicate sessaoPredicate = builder.equal(root.get("sessao").get("id"), sessaoId);

            // Aplicando GROUP BY voto
            query.groupBy(root.get("voto"));

            return sessaoPredicate;
        };
    }
}
