package com.siscred.cooperativa.infrastructure.gateways.kafka.consumer;

import com.siscred.cooperativa.application.usecases.RegistrarVotoUsecase;
import com.siscred.cooperativa.infrastructure.enuns.TipoOprecaoEnum;
import com.siscred.cooperativa.infrastructure.gateways.kafka.dto.VotoKafkaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class VotoKafkaConsumerGateway {

    private final RegistrarVotoUsecase registrarVotoUsecase;

    public VotoKafkaConsumerGateway(RegistrarVotoUsecase registrarVotoUsecase) {
        this.registrarVotoUsecase = registrarVotoUsecase;
    }

    @KafkaListener(topics = "${kafka.topic}", groupId = "voto-group", containerFactory = "votoKafkaListenerContainerFactory")
    public void listen(VotoKafkaDTO votoKafkaDTO) {
        if (Objects.isNull(votoKafkaDTO) || Objects.isNull(votoKafkaDTO.getTipoOprecao())) {
            log.warn("Received null or invalid payload: {}", votoKafkaDTO);
            return;
        }
        log.info("Received message: {}", votoKafkaDTO);
        if (TipoOprecaoEnum.REGISTRA_VOTO.name().equals(votoKafkaDTO.getTipoOprecao().name())) {
            log.info("Processing vote registration for {}", votoKafkaDTO.getVotoDomain());
            registrarVotoUsecase.execute(votoKafkaDTO.getVotoDomain());
        } else {
            log.warn("Unknown operation type: {}", votoKafkaDTO.getTipoOprecao());
        }
    }

}
