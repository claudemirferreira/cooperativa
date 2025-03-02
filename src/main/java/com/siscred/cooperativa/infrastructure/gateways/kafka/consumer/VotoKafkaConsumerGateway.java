package com.siscred.cooperativa.infrastructure.gateways.kafka.consumer;

import com.siscred.cooperativa.application.usecases.RegistrarVotoUsecase;
import com.siscred.cooperativa.domain.VotoDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VotoKafkaConsumerGateway {

    private final RegistrarVotoUsecase registrarVotoUsecase;

    public VotoKafkaConsumerGateway(RegistrarVotoUsecase registrarVotoUsecase) {
        this.registrarVotoUsecase = registrarVotoUsecase;
    }

    @KafkaListener(topics = "${kafka.topic}", groupId = "voto-group", containerFactory = "votoKafkaListenerContainerFactory")
    public void listen(VotoDomain votoDomain) {
        log.info("######################################################");
        log.info("processed payload {}", votoDomain);
        registrarVotoUsecase.execute(votoDomain);
    }

}
