package com.siscred.cooperativa.infrastructure.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.siscred.cooperativa.domain.TotalVotoDomain;
import org.springframework.jdbc.core.RowMapper;

public class TotalVotoDomainRowMapper implements RowMapper<TotalVotoDomain> {

    @Override
    public TotalVotoDomain mapRow(ResultSet rs, int rowNum) throws SQLException {
        TotalVotoDomain totalVoto = new TotalVotoDomain();
        totalVoto.setSessaoId(rs.getLong("sessaoId"));
        totalVoto.setTotalSim(rs.getInt("totalSim"));
        totalVoto.setTotalNao(rs.getInt("totalNao"));
        return totalVoto;
    }
}
