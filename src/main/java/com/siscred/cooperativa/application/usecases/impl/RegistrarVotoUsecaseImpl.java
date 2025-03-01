package com.siscred.cooperativa.application.usecases.impl;

import com.siscred.cooperativa.application.gateways.VotoGateway;
import com.siscred.cooperativa.application.usecases.RegistrarVotoUsecase;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.exception.ExistVotoCPFException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RegistrarVotoUsecaseImpl implements RegistrarVotoUsecase {

    private final VotoGateway votoGateway;

    @Autowired
    public RegistrarVotoUsecaseImpl(VotoGateway votoGateway) {
        this.votoGateway = votoGateway;
    }

    @Override
    @Transactional
    public VotoDomain execute(VotoDomain votoDomain) {
        try {
            // Verifique se já existe um voto antes de criar
            ensureVotoDoesNotExist(votoDomain.getSessao().getId(), votoDomain.getCpf());
            votoGateway.create(votoDomain);
        } catch (ExistVotoCPFException e) {
            log.info("Já existe um voto para o CPF informado.");
            throw e;  // Lançando a exceção para garantir que o teste capture a falha
        } catch (Exception e) {
            log.info("Erro desconhecido: {}", e.getMessage());
            throw e;  // Lançando a exceção genérica caso ocorra algo inesperado
        }
        return votoDomain;
    }

    private void ensureVotoDoesNotExist(Long sessaoId, String cpf) {
        if (!votoGateway.findBySessaoIdAndCpf(sessaoId, cpf).isEmpty()) {
            throw new ExistVotoCPFException("Já existe um voto para o CPF informado.");
        }
    }

}
