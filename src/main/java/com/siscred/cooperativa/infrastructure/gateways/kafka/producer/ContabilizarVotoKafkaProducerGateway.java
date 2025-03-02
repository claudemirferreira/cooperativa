package com.siscred.cooperativa.infrastructure.gateways.kafka.producer;

import com.siscred.cooperativa.application.gateways.ContabilizarVotoMensageriaGateway;
import com.siscred.cooperativa.domain.TotalVotoDomain;
import com.siscred.cooperativa.infrastructure.enuns.TipoOprecaoEnum;
import com.siscred.cooperativa.infrastructure.gateways.kafka.dto.VotoKafkaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContabilizarVotoKafkaProducerGateway implements ContabilizarVotoMensageriaGateway {

    private final KafkaTemplate<String, VotoKafkaDTO> kafkaTemplate;

    private final String topic;

    public ContabilizarVotoKafkaProducerGateway(KafkaTemplate<String, VotoKafkaDTO> kafkaTemplate,
                                                @Value("${kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void send(TotalVotoDomain totalVotoDomain) {
        log.info("call send {} payload {}", topic, totalVotoDomain);
        VotoKafkaDTO votoKafkaDTO = VotoKafkaDTO
                .builder()
                .tipoOprecao(TipoOprecaoEnum.CONTABILIZAR_VOTO)
                .totalVotoDomain(totalVotoDomain)
                .build();
        kafkaTemplate.send(topic, votoKafkaDTO);
    }
}
