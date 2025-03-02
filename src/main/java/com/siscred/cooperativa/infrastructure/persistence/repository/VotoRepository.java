package com.siscred.cooperativa.infrastructure.persistence.repository;

import com.siscred.cooperativa.infrastructure.persistence.entity.Voto;
import com.siscred.cooperativa.infrastructure.persistence.iquery.ITotalVoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VotoRepository extends JpaRepository<Voto, Long>, JpaSpecificationExecutor<Voto> {

    @Query(value = """
                SELECT
                    p.nome AS pauta,
                    s.sessao_id AS sessaoId,
                    COALESCE(SUM(CASE WHEN v.voto = 'SIM' THEN 1 ELSE 0 END), 0) AS totalSim,
                    COALESCE(SUM(CASE WHEN v.voto = 'NÃO' THEN 1 ELSE 0 END), 0) AS totalNao
                FROM pauta p
                LEFT JOIN sessao s ON p.pauta_id = s.pauta_id
                LEFT JOIN voto v ON s.sessao_id = v.sessao_id
                WHERE STATUS = 'ABERTO'
                AND FIM < NOW()
                AND STATUS = 'ABERTO'
                GROUP BY p.nome, s.sessao_id
                ORDER BY p.nome DESC
            """, nativeQuery = true)
    List<ITotalVoto> countVotoSesaoAberta();
}
