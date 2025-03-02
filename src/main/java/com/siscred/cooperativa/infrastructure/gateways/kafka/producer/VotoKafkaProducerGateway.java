package com.siscred.cooperativa.infrastructure.gateways.kafka.producer;

import com.siscred.cooperativa.application.gateways.VotoMensageriaGateway;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.infrastructure.enuns.TipoOprecaoEnum;
import com.siscred.cooperativa.infrastructure.gateways.kafka.dto.VotoKafkaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VotoKafkaProducerGateway implements VotoMensageriaGateway {

    private final KafkaTemplate<String, VotoKafkaDTO> kafkaTemplate;

    private final String topic;

    public VotoKafkaProducerGateway(KafkaTemplate<String, VotoKafkaDTO> kafkaTemplate,
                                    @Value("${kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void send(VotoDomain votoDomain) {
        log.info("call send {} payload {}", topic, votoDomain);
        VotoKafkaDTO votoKafkaDTO = new VotoKafkaDTO(TipoOprecaoEnum.REGISTRA_VOTO, votoDomain);
        kafkaTemplate.send(topic, votoKafkaDTO);
    }
}
