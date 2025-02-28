package com.siscred.cooperativa.application.usecases;

import com.siscred.cooperativa.domain.SessaoVotacaoDomain;

public interface CreateSessaoVotacaoUsecase {

    SessaoVotacaoDomain execute(Long pautaId, Integer tempoExpiracaoEmMinutos);
}
