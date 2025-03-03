package com.siscred.cooperativa.infrastructure.controller;

import com.siscred.cooperativa.application.usecases.CreateSessaoUsecase;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.infrastructure.controller.dto.response.CreateSessaoVotacaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/sessao/v1")
public class SessaoController {

    private final CreateSessaoUsecase createSessaoUsecase;
    private final ModelMapper modelMapper;

    public SessaoController(CreateSessaoUsecase createSessaoUsecase, ModelMapper modelMapper) {
        this.createSessaoUsecase = createSessaoUsecase;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Cria uma nova Sessão de Votação", description = """
            <h3>Retorna a Sessão de Votação criada.</h3>
            """)
    @PostMapping("/pauta/{pautaId}")
    public ResponseEntity<CreateSessaoVotacaoResponse> create(
            @PathVariable Long pautaId,
            @RequestParam(required = false, defaultValue = "1") Integer tempoExpiracaoEmMinutos) { // Definindo 1 minuto como padrão

        SessaoDomain sessaoDomain = createSessaoUsecase.execute(pautaId, tempoExpiracaoEmMinutos);

        return ResponseEntity.created(URI.create("/sessao-votacao/" + sessaoDomain.getId())).body(
                modelMapper.map(sessaoDomain, CreateSessaoVotacaoResponse.class)
        );
    }
}
