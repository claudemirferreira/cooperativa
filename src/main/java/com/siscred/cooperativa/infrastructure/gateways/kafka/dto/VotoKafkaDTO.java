package com.siscred.cooperativa.infrastructure.gateways.kafka.dto;

import com.siscred.cooperativa.domain.TotalVotoDomain;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.enuns.TipoOprecaoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotoKafkaDTO {
    private TipoOprecaoEnum tipoOprecao;
    private VotoDomain votoDomain;
    private TotalVotoDomain totalVotoDomain;
}
