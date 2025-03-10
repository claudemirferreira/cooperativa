package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.SessaoGateway;
import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.application.usecases.ContabilizarVotoUsecase;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.domain.TotalVotoDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import com.siscred.cooperativa.infrastructure.gateways.kafka.producer.ContabilizarVotoKafkaProducerGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ContabilizarVotoUsecaseImpl implements ContabilizarVotoUsecase {

    private final VotoGateway votoGateway;
    private final SessaoGateway sessaoGateway;
    private final ContabilizarVotoKafkaProducerGateway contabilizarVotoKafkaProducerGateway;

    @Autowired
    public ContabilizarVotoUsecaseImpl(
            VotoGateway votoGateway,
            SessaoGateway sessaoGateway,
            ContabilizarVotoKafkaProducerGateway contabilizarVotoKafkaProducerGateway) {
        this.votoGateway = votoGateway;
        this.sessaoGateway = sessaoGateway;
        this.contabilizarVotoKafkaProducerGateway = contabilizarVotoKafkaProducerGateway;
    }

    @Override
    public void execute() {
        List<TotalVotoDomain> listaTotalVoto = this.votoGateway.countVotoSesaoAberta();
        listaTotalVoto.forEach(totalVotoDomain -> {
            log.info("{}", totalVotoDomain);
            this.fecharSessao(totalVotoDomain.getSessaoId());
            contabilizarVotoKafkaProducerGateway.send(totalVotoDomain);
        });
    }

    private void fecharSessao(Long id) {
        SessaoDomain sessaoDomain = this.sessaoGateway.findById(id);
        sessaoDomain.setStatus(StatusEnum.FECHADO);
        sessaoGateway.save(sessaoDomain);
    }

}
