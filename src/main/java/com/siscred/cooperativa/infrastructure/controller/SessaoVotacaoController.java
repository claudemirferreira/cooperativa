package com.siscred.cooperativa.infrastructure.controller;

import com.siscred.cooperativa.application.usecases.CreateSessaoVotacaoUsecase;
import com.siscred.cooperativa.domain.SessaoVotacaoDomain;
import com.siscred.cooperativa.infrastructure.controller.dto.response.CreateSessaoVotacaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/sessao-votacao")
public class SessaoVotacaoController {

    private final CreateSessaoVotacaoUsecase createSessaoVotacaoUsecase;
    private final ModelMapper modelMapper;

    public SessaoVotacaoController(CreateSessaoVotacaoUsecase createSessaoVotacaoUsecase, ModelMapper modelMapper) {
        this.createSessaoVotacaoUsecase = createSessaoVotacaoUsecase;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Cria uma nova Sessão de Votação", description = """
            <h3>Retorna a Sessão de Votação criada.</h3>
            """)
    @PostMapping("/pauta/{pautaId}")
    public ResponseEntity<CreateSessaoVotacaoResponse> createSessaoVotacao(
            @PathVariable Long pautaId,
            @RequestParam(required = false, defaultValue = "1") Integer tempoExpiracaoEmMinutos) { // Definindo 1 minuto como padrão

        SessaoVotacaoDomain sessaoVotacaoDomain = createSessaoVotacaoUsecase.execute(pautaId, tempoExpiracaoEmMinutos);

        return ResponseEntity.created(URI.create("/sessao-votacao/" + sessaoVotacaoDomain.getId())).body(
                modelMapper.map(sessaoVotacaoDomain, CreateSessaoVotacaoResponse.class)
        );
    }
}
