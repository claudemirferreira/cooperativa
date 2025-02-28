package com.siscred.cooperativa.application.usecases;

import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.exception.CpfInvalidException;
import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;

public interface CreateVotoUsecase {

    VotoDomain execute(String cpf, Long sessaoId, VotoEnum voto) throws CpfInvalidException;
}
