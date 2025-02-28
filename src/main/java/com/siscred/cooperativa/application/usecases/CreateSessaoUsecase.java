package com.siscred.cooperativa.application.usecases;

import com.siscred.cooperativa.domain.SessaoDomain;

public interface CreateSessaoUsecase {

    SessaoDomain execute(Long pautaId, Integer tempoExpiracaoEmMinutos);
}
