package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.SessaoGateway;
import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.application.usecases.ContabilizarVotoUsecase;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.domain.TotalVotoDomain;
import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ContabilizarVotoUsecaseImpl implements ContabilizarVotoUsecase {

    private final VotoGateway votoGateway;
    private final SessaoGateway sessaoGateway;

    @Autowired
    public ContabilizarVotoUsecaseImpl(VotoGateway votoGateway, SessaoGateway sessaoGateway) {
        this.votoGateway = votoGateway;
        this.sessaoGateway = sessaoGateway;
    }

    @Override
    public void execute() {
        List<TotalVotoDomain> listaTotalVoto = this.votoGateway.countVotoSesaoAberta();
        listaTotalVoto.forEach(item -> {
            log.info("{}", item);
            this.fecharSessao(item.getSessaoId());

        });
    }

    private void fecharSessao(Long id) {
        SessaoDomain sessaoDomain = this.sessaoGateway.findById(id);
        sessaoDomain.setStatus(StatusEnum.FECHADO);
        sessaoGateway.save(sessaoDomain);
    }

}
